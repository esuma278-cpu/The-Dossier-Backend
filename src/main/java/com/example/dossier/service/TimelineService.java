package com.example.dossier.service;

import com.example.dossier.entity.Timeline;
import com.example.dossier.repository.TimelineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineService {

    private final TimelineRepository timelineRepository;

    public TimelineService(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    public Timeline addTimelineEvent(Timeline timeline) {
        return timelineRepository.save(timeline);
    }

    public List<Timeline> getAllTimelineEvents() {
        return timelineRepository.findAll();
    }

    public Timeline getTimelineEventById(Long id) {
        return timelineRepository.findById(id).orElse(null);
    }

    public List<Timeline> getTimelineByCase(Long caseId) {
        return timelineRepository.findByInvestigationCaseId(caseId);
    }

    public Timeline updateTimelineEvent(Long id, Timeline updatedTimeline) {
        Timeline existingTimeline =
                timelineRepository.findById(id).orElse(null);

        if (existingTimeline == null) {
            return null;
        }

        existingTimeline.setEventTitle(updatedTimeline.getEventTitle());
        existingTimeline.setEventDescription(
                updatedTimeline.getEventDescription());
        existingTimeline.setEventDate(updatedTimeline.getEventDate());

        return timelineRepository.save(existingTimeline);
    }

    public boolean deleteTimelineEvent(Long id) {
        if (!timelineRepository.existsById(id)) {
            return false;
        }

        timelineRepository.deleteById(id);
        return true;
    }
}