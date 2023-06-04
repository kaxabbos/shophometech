package com.shophometech.controller;

import com.shophometech.controller.main.Attributes;
import com.shophometech.model.Appliances;
import com.shophometech.model.Stats;
import com.shophometech.model.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/appliances")
public class AppliancesCont extends Attributes {
    @GetMapping("/{id}")
    public String appliances(Model model, @PathVariable Long id) {
        addAttributesAppliances(model, id);
        return "appliances";
    }

    @GetMapping("/add")
    public String appliancesAdd(Model model) {
        addAttributesAppliancesAdd(model);
        return "appliancesAdd";
    }

    @PostMapping("/add")
    public String appliancesAddNew(Model model, @RequestParam String name, @RequestParam double price, @RequestParam Category category, @RequestParam int discount, @RequestParam int warranty, @RequestParam String country, @RequestParam String firm, @RequestParam int quantity, @RequestParam String date, @RequestParam MultipartFile file) {
        Appliances appliances = new Appliances(name, price, category, warranty, country, firm, quantity, date, discount);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось загрузить изображение");
                addAttributesAppliancesAdd(model);
                return "appliancesAdd";
            }

            appliances.setFile(res);
        }
        Stats stats = new Stats();
        appliances.setStats(stats);
        stats.setAppliances(appliances);
        appliancesRepo.save(appliances);
        statsRepo.save(stats);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String appliancesEdit(Model model, @PathVariable Long id) {
        addAttributesAppliancesEdit(model, id);
        return "appliancesEdit";
    }

    @PostMapping("/edit/{id}")
    public String appliancesEditOld(Model model, @RequestParam String name, @RequestParam double price, @RequestParam Category category, @RequestParam int warranty, @RequestParam int discount, @RequestParam String country, @RequestParam String firm, @RequestParam int quantity, @RequestParam String date, @RequestParam MultipartFile file, @PathVariable Long id) {
        Appliances appliances = appliancesRepo.getReferenceById(id);
        appliances.update(name, price, category, warranty, country, firm, quantity, date, discount);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось загрузить изображение");
                addAttributesAppliancesEdit(model, id);
                return "appliancesEdit";
            }
            appliances.setFile(res);
        }

        appliancesRepo.save(appliances);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String appliancesDelete(@PathVariable Long id) {
        appliancesRepo.deleteById(id);
        return "redirect:/";
    }
}
