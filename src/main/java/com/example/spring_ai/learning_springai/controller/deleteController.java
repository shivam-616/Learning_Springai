package com.example.spring_ai.learning_springai.controller;


import com.example.spring_ai.learning_springai.service.NoteManagementService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/delete")
@RestController
public class deleteController {

private NoteManagementService  noteManagementService;
    public deleteController( NoteManagementService  noteManagementService) {
        this.noteManagementService = noteManagementService;
    }

    @DeleteMapping("/notes")
    public String deleteNotes(@RequestParam String subject) {
        noteManagementService.deleteNotesBySubject(subject);
        return "All notes for " + subject + " have been removed.";
    }
}
