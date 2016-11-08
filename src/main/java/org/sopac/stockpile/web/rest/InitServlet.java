package org.sopac.stockpile.web.rest;

import org.sopac.stockpile.repository.ItemRepository;
import org.sopac.stockpile.service.ItemService;
import org.sopac.stockpile.service.impl.ItemServiceImpl;
import org.sopac.stockpile.service.mapper.ItemMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by sachin on 11/8/16.
 */
@WebServlet(name = "InitServlet")
public class InitServlet extends HttpServlet {

    //@Inject
    //private ItemRepository itemRepository;

    //@Inject
    //private ItemMapper itemMapper;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.write(new Date().toString() + "<br/>Sachindra Singh<hr/>");


        //String tmp = String.valueOf(itemRepository.findAll().size());
        //out.write(tmp);

        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
