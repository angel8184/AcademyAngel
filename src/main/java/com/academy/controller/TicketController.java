package com.academy.controller;

import com.academy.dao.TicketDao;
import com.academy.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketDao dao;

    @PostMapping("/bookTickets")
    public String bookTicket(@RequestBody List<Ticket> tickets){

        dao.save(tickets);

        return "ticket booked : " + tickets.size();

    }

    @PostMapping("/getTickets")
    public List<Ticket> getTickets(){
        return (List<Ticket>)dao.findAll();
    }
}
