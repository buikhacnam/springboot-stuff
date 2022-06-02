package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.service.FutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "api/v1/future")
public class FutureController {

    @Autowired
    private FutureService futureService;

    @PostMapping("/")
    public void post() {
        System.out.println("FutureController.post()");
        List<String> users = new ArrayList<>();
        users.add("buinam");
        users.add("buinam2");
        users.add("buinam3");
        for (String user : users) {
              futureService.saveUsers(user);
        }
        System.out.println("FutureController.post() end");

        /*
        FutureController.post()
        FutureController.post() end
        Saving user: buinam2 from thread userThread-2
        Saving user: buinam from thread userThread-1
        Saved user: buinam - userThread-1 from thread userThread-1
        Saved user: buinam2 - userThread-2 from thread userThread-2
        Saving user: buinam3 from thread userThread-2
        Saved user: buinam3 - userThread-2 from thread userThread-2
        */
        // ----------------------------------------------------------------------------

        // if no AsyncConfig Class, then the log:
        /*
        FutureController.post()
        Saving user: buinam from thread http-nio-8888-exec-2
        Saved user: buinam - http-nio-8888-exec-2 from thread http-nio-8888-exec-2
        Saving user: buinam2 from thread http-nio-8888-exec-2
        Saved user: buinam2 - http-nio-8888-exec-2 from thread http-nio-8888-exec-2
        Saving user: buinam3 from thread http-nio-8888-exec-2
        Saved user: buinam3 - http-nio-8888-exec-2 from thread http-nio-8888-exec-2
        FutureController.post() end
        */
    }


    @PostMapping("/1")
    public void post1() {
        System.out.println("FutureController.post()");
        CompletableFuture<String> future1 = futureService.saveUsers("buinam");
        CompletableFuture<String> future2 = futureService.saveUsers("buinam2");
        CompletableFuture.allOf(future1, future2).join();
        System.out.println("FutureController.post() end");

        /*
        FutureController.post()
        Saving user: buinam from thread userThread-1
        Saving user: buinam2 from thread userThread-2
        Saved user: buinam2 - userThread-2 from thread userThread-2
        Saved user: buinam - userThread-1 from thread userThread-1
        FutureController.post() end
        */
        // ----------------------------------------------------------------------------
        // if no AsyncConfig Class, then the log:
        /*
        FutureController.post()
        Saving user: buinam from thread http-nio-8888-exec-5
        Saved user: buinam - http-nio-8888-exec-5 from thread http-nio-8888-exec-5
        Saving user: buinam2 from thread http-nio-8888-exec-5
        Saved user: buinam2 - http-nio-8888-exec-5 from thread http-nio-8888-exec-5
        FutureController.post() end
        */

    }
}
