package com.secor.fdamenuservice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class MainRestController {

    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    @Qualifier("profile_service_get_restros")
    WebClient webClientProfileServiceGetRestros;

    @GetMapping("menu")
    public ResponseEntity<?> getMenu(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        // COOKIE VALIDATION LOGIC
        List<Cookie> cookieList = null;

        //Optional<String> healthStatusCookie = Optional.ofNullable(request.getHeader("health_status_cookie"));
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null)
        {
            cookieList = new ArrayList<>();
        }
        else
        {
            // REFACTOR TO TAKE NULL VALUES INTO ACCOUNT
            cookieList = List.of(cookies);
        }

        if( cookieList.stream().filter(cookie -> cookie.getName().equals("menu-service-getmenu-1")).findAny().isEmpty()) // COOKIE_CHECK
        {
            // FRESH REQUEST PROCESSING

            MenuView menuview = new MenuView();

            List<Dish> dishes =dishRepository.findAll();
            // Restro List has to be received from the Profile Service before
            // further processing can be done

            // FIRST PART OF ASYNC REQUEST - TO SEND OUT THE REQUEST
            Flux<RestroView> responseFlux = webClientProfileServiceGetRestros.get()
                    //.body(citizenid, UUID.class) // Set the request body as UUID
                    .retrieve()
                    .bodyToFlux(RestroView.class); // ASYNCHRONOUS

            Integer cookie_value =  new Random().nextInt();
            Cookie cookieGetMenu_1 = new Cookie("menu-service-getmenu-1", cookie_value.toString());
            cookieGetMenu_1.setMaxAge(300);


            // SECOND PART OF ASYNC REQUEST - TO SET UP THE HANDLER FOR THE EVENTUAL RESPONSE
            responseFlux.subscribe(
                    (response) -> {
                        log.info(response+" from the profile service");
                        // MENU CREATION LOGIC TO BE IMPLEMENTED HERE
                        // AND PUT THE RESPONSE IN REDIS
                        //redisTemplate.opsForValue().set(credential.getCitizenid().toString(), credential.getPassword());
                    },
                    error ->
                    {
                        log.info("error processing the response "+error.getMessage());
                        redisTemplate.opsForValue().
                                set(String.valueOf(cookieGetMenu_1.getName()+cookieGetMenu_1.getValue()), error.getMessage());
                    });

            redisTemplate.opsForValue().set(String.valueOf(cookieGetMenu_1.getName()+cookieGetMenu_1.getValue()), null);
            httpServletResponse.addCookie(cookieGetMenu_1);
            return ResponseEntity.ok("Menu is being generated..."); // INTERIM RESPONSE ONE

        }
        else
        {
            // FOLLOW UP REQUEST LOGIC
            // CHECK IF THE COOKIE IS VALID IN REDIS
            // IF VALID, THEN RETURN THE RESPONSE FROM THE FIRST REQUEST
            // IF NOT VALID, THEN RETURN A 400 BAD REQUEST

            Cookie followup_cookie =  cookieList.stream().
                    filter(cookie -> cookie.getName().equals("menu-service-getmenu-1")).findAny().get();

            String followup_cookie_key = followup_cookie.getName()+followup_cookie.getValue();
            String response = (String)redisTemplate.opsForValue().get(followup_cookie_key);

            if(response == null)
            {
                return ResponseEntity.ok("Request still under process...");
            }
            else if(response.contains("SUCCESS"))
            {
                return ResponseEntity.ok("THIS IS THE MENU YOU WERE LOOKING HERE");
            }
            else if(response.contains("408"))
            {
                return ResponseEntity.ok("Could not connect to Profile-Service in time... [WILL RETRY]");
            }
            else if(response.contains("404"))
            {
                return ResponseEntity.ok("Fetching Restro Details from Profile-Service Failed... [WILL RETRY]");
            }
            else if(response.contains("500"))
            {
                return ResponseEntity.ok("Profile-Service messed up... [WILL RETRY]");
            }
            else
            {
                return ResponseEntity.badRequest().body("Something went wrong...");
            }



        }



    }


}
