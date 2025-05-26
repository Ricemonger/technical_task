package technikal.task.fishmarket.controllers;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import technikal.task.fishmarket.models.Fish;
import technikal.task.fishmarket.models.FishDto;
import technikal.task.fishmarket.services.FishRepository;

@Controller
@RequestMapping("/fish")
public class FishController {

    @Autowired
    private FishRepository repo;

    @GetMapping({"", "/"})
    public String showFishList(Model model) {
        List<Fish> fishlist = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("fishlist", fishlist);
        return "index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        FishDto fishDto = new FishDto();
        model.addAttribute("fishDto", fishDto);
        return "createFish";
    }

    @GetMapping("/delete")
    public String deleteFish(@RequestParam int id) {

        try {

            Fish fish = repo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + fish.getImageFileNames());
            Files.delete(imagePath);
            repo.delete(fish);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/fish";
    }

    @PostMapping("/create")
    public String addFish(@Valid @ModelAttribute FishDto fishDto, BindingResult result) {

        List<MultipartFile> imageFiles = fishDto.getImageFiles();

        if ((imageFiles == null) || imageFiles.stream().allMatch(MultipartFile::isEmpty)) {
            result.addError(new FieldError("fishDto", "imageFiles", "Потрібні фото рибки"));
        }

        if (result.hasErrors()) {
            return "createFish";
        }

        Date catchDate = new Date();

        List<String> storageFileNames = new ArrayList<>();

        String uploadDir = "public/images/";
        Path uploadPath = Paths.get(uploadDir);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        for (MultipartFile imageFile : fishDto.getImageFiles()) {
            if (!imageFile.isEmpty()) {
                String storageFileName = catchDate.getTime() + "_" + imageFile.getOriginalFilename();
                try (InputStream inputStream = imageFile.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + catchDate.getTime() + "_" + imageFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                storageFileNames.add(storageFileName);
            }
        }

        Fish fish = new Fish();

        fish.setCatchDate(catchDate);
        fish.setImageFileNames(storageFileNames);
        fish.setName(fishDto.getName());
        fish.setPrice(fishDto.getPrice());

        repo.save(fish);

        return "redirect:/fish";
    }

}
