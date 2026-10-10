package com.example.dossier.controller;

import com.example.dossier.entity.Timeline;
import com.example.dossier.service.TimelineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://the-dossier-front-end.vercel.app"
})
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @PostMapping
    public Timeline addTimelineEvent(@RequestBody Timeline timeline) {
        return timelineService.addTimelineEvent(timeline);
    }

    @GetMapping
    public List<Timeline> getAllTimelineEvents() {
        return timelineService.getAllTimelineEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timeline> getTimelineEventById(
            @PathVariable Long id) {

        Timeline timeline = timelineService.getTimelineEventById(id);

        if (timeline == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(timeline);
    }

    @GetMapping("/case/{caseId}")
    public List<Timeline> getTimelineByCase(@PathVariable Long caseId) {
        return timelineService.getTimelineByCase(caseId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Timeline> updateTimelineEvent(
            @PathVariable Long id,
            @RequestBody Timeline updatedTimeline) {

        Timeline timeline =
                timelineService.updateTimelineEvent(id, updatedTimeline);

        if (timeline == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(timeline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimelineEvent(@PathVariable Long id) {
        boolean deleted = timelineService.deleteTimelineEvent(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}