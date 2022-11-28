package com.example.semestr.servlets.file;

import com.example.semestr.entities.FileDC;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.DecorationPages;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/file/edit")
public class EditFileServlet extends HttpServlet {


    private CRUDRepositoryFileImpl repositoryFile;


    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idFile = Long.valueOf(request.getParameter("idFile"));
        FileDC fileDC = repositoryFile.findById(idFile);

        if (!(SecurityService.isAccess(request, fileDC.getHolderId()))) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/no_access.jsp").forward(request, response);
            return;
        }

        DecorationPages.setTitle(request,"Edit: " + fileDC.getTitle());

        request.setAttribute("title", fileDC.getTitle());
        request.setAttribute("description", fileDC.getDescription());
        request.setAttribute("idFile", idFile);

        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/edit_file.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newTitle = request.getParameter("title");
        String newDescription = request.getParameter("description");
        boolean newPublicAccess = request.getParameter("public_access") != null; //== null ? false : true

        Long idFile = Long.valueOf(request.getParameter("idFile"));
        FileDC oldFile = repositoryFile.findById(idFile);

        if (newTitle.isEmpty()){
            request.setAttribute("message", "Title is empty");
            this.doGet(request,response);
            return;
        }

        FileDC newFileDC = FileDC.builder()
                .id(idFile)
                .title(newTitle)
                .description(newDescription)
                .holderId(oldFile.getHolderId())
                .nameFile(oldFile.getNameFile())
                .publicAccess(newPublicAccess)
                .build();

        repositoryFile.update(newFileDC);
        

        response.sendRedirect(getServletContext().getContextPath() + "/files/my");
    }
}
