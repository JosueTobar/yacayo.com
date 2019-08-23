/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.control;

import com.yacayo.dao.EmpresaDAO;
import com.yacayo.dao.UsuarioDAO;
import java.io.IOException;
import javax.inject.Inject;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david.poncefgkss
 */
@WebServlet(name = "Empresa", urlPatterns = {"/empresa"})
public class Empresa extends HttpServlet {

    @Inject
    EmpresaDAO eDao;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("empresa", eDao.findEmpresa(1));
        request.getRequestDispatcher("").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String accion = request.getParameter("accion");
        Integer id = request.getParameter("txtId") != null ? Integer.parseInt(request.getParameter("txtId")) : null;
        
        String us = request.getParameter("txtUs") != null ? request.getParameter("txtUs") : "";
        String pass = request.getParameter("txtPass") != null ? request.getParameter("txtPass") : "";
        String tipo = "Postulante";
        
        String nombre = request.getParameter("txtNombre") != null ? request.getParameter("txtNombre") : "";
        String tel = request.getParameter("txtTel") != null ? request.getParameter("txtTel") : "";
        String direc = request.getParameter("txtde") != null ? request.getParameter("txtTel") : "";

        switch (accion) {

            case "Crear":
                try{
                    eDao.create(new com.yacayo.entity.Empresa(nombre, tel, direc));
                }catch(Exception e){
                    request.getRequestDispatcher("usuarios/empresa.xhtml?Error").forward(request, response);                    
                }
                break;
            case "Modificar":
                break;
            case "Eliminar":
                break;
        }
        
        

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
