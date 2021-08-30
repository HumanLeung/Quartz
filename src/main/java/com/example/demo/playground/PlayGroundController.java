package com.example.demo.playground;

import com.example.demo.info.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timer")
public class PlayGroundController {
    private final PlayGroundService service;

    @Autowired
    public PlayGroundController(PlayGroundService playGroundService){
        this.service = playGroundService;
    }

    @PostMapping("/helloworld")
    public void runHelloWorldJob() {
        service.runHelloWorldJob();
    }
    @GetMapping
    public List<Object> getAllRunningTimers() {
        return service.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public TimerInfo getRunningTimer(@PathVariable String timerId) {
        return service.getRunningTimer(timerId);
    }

    @DeleteMapping("/{timerId}")
    public Boolean deleteTimer(@PathVariable String timerId) {
        return service.deleteTimer(timerId);
    }
}
