package br.edu.ufrn.minimall.controller;

import br.edu.ufrn.minimall.model.Miniatura;
import br.edu.ufrn.minimall.service.FileService;
import br.edu.ufrn.minimall.service.MiniaturaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MiniaturaController {

    private int cont;
    MiniaturaService service;

    @Autowired
    FileService fileService;

    public MiniaturaController() {
    }

    public MiniaturaController(MiniaturaService service, FileService fileService) {
        this.service = service;
        this.fileService = fileService;
    }


    @GetMapping("/")
    public String getIndex(Model model, HttpServletResponse response, HttpSession session) {

        List<Miniatura> miniatura = service.findAll();
        model.addAttribute("miniatura", miniatura);

        model.addAttribute("miniatura", miniatura);

        Cookie cookie = new Cookie("visita", "cookie-value");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        return "index";
    }

    @GetMapping("/cadastrar")
    public String doCadastrar(Model model) {
        Miniatura m = new Miniatura();
        model.addAttribute("miniatua", m);

        return "cadastrar";
    }

    @GetMapping("deletar/{id}")
    public String deletarMiniatura(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Miniatura m = service.findById(id);
        m.setDeleted(new Date(System.currentTimeMillis()));
        service.create(m);
        redirectAttributes.addAttribute("msg", "Deletado");
        return "redirect:/";
    }


    @GetMapping("editar/{id}")
    public String getEditarMiniatura(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {

        Miniatura miniatura = service.findById(id);
        model.addAttribute("miniatura", miniatura);

        redirectAttributes.addAttribute("msg2", "Cadastro Atualizado");
        return "cadastrar";
    }

    @PostMapping("/salvar")
    public String doSalvarMiniatura(@ModelAttribute @Valid Miniatura m, Errors errors,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (errors.hasErrors()) {
            redirectAttributes.addAttribute("msg", "Cadastro Não realizado");
            return "redirect:/";
        } else {
            try {
                m.setImagem(file.getOriginalFilename());
                service.update(m);
                fileService.save(file);

                redirectAttributes.addFlashAttribute("msgSucesso", "Salvo com sucesso");
                return "index";
            } catch (Exception e) {
                redirectAttributes.addAttribute("msg", "erro ao Cadastrar !");
                return "index";
            }
        }
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Miniatura> lista = new ArrayList<>();
        lista = service.findAll();

        model.addAttribute("miniaturas", lista);

        return "admin";
    }


    @GetMapping("/adicionarCarrinho/{id}")
    public String doAdicionarItem(Model model, @PathVariable(name = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int contCarrinho = 0;

        HttpSession session = request.getSession();
        List<Miniatura> carrinho = (List<Miniatura>) session.getAttribute("carrinho");
        Miniatura miniatura = service.findById(id);
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        carrinho.add(miniatura);
        cont = carrinho.size();
        session.setAttribute("carrinho", carrinho);


        List<Miniatura> computadores = service.findAll();
        List<Miniatura> miniaturaBoa = new ArrayList<>();

        computadores.forEach(listaMiniatura -> {
            if (listaMiniatura.getDeleted() != null) {
                miniaturaBoa.add(listaMiniatura);
            }
        });
        model.addAttribute("miniatura", miniaturaBoa);

        return "index";
    }

    @GetMapping("/remover/{id}")
    public String removerItemCarrinho(@PathVariable(name = "id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Miniatura m = service.findById(id);
        List<Miniatura> carrinho = (List<Miniatura>) session.getAttribute("carrinho");
        for (int i = 0; i < carrinho.size(); i++) {
            if (carrinho.get(i).equals(m)) {
                carrinho.remove(m);
            }
        }
        session.setAttribute("carrinho", carrinho);

        return "vercarrinho";
    }

    @GetMapping("/vercarrinho")
    public String getVerCarrinho(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        List<Miniatura> carrinho = (List<Miniatura>) session.getAttribute("carrinho");
        if (session.getAttribute("carrinho") == null) {
            redirectAttributes.addAttribute("msg", "Carrinho vazio");
            return "redirect:/";
        }
        model.addAttribute("miniatura", carrinho);
        return "vercarrinho";
    }
}
