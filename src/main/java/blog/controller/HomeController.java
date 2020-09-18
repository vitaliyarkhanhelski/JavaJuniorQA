package blog.controller;

import blog.comparator.SortByCharAndName;
import blog.model.Message;
import blog.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@Controller
public class HomeController {

    private MessageService messageService;

    public HomeController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String findAll(ModelMap map) {
        map.put("letters", MessageService.letters);
        map.put("status", "Main");

        List<Message> list = messageService.findAll();

        if (list.isEmpty()) map.put("noRecords", "No Records Found");
        else map.put("messages", list);

        return "index";
    }


    @GetMapping("/showFavorites")
    public String showFavorites(ModelMap map) {
        map.put("letters", MessageService.letters);
        map.put("page", "f");
        map.put("status", "Favorites");
        List<Message> list = messageService.findAllFavorites();
        Collections.sort(list, new SortByCharAndName());
        map.put("messages", list);

        if (list.isEmpty()) map.put("noRecords", "No Records Found");
        return "index";
    }


    @GetMapping("/favorite")
    public String isFavorite(ModelMap map, @RequestParam("messageId") int id, @RequestParam("page") String page) {
        messageService.checkFavorite(id);
        if (page.equals("f")) return "redirect:/showFavorites";
        if (page.equals("c")) return "redirect:/showToComplete";
        return "redirect:";
    }


    @GetMapping("/showToComplete")
    public String showToComplete(ModelMap map) {
        map.put("letters", MessageService.letters);
        map.put("page", "c");
        map.put("status", "To Complete");
        List<Message> list = messageService.findAllToComplete();
        Collections.sort(list, new SortByCharAndName());
        map.put("messages", list);

        if (list.isEmpty()) map.put("noRecords", "No Records Found");
        return "index";
    }


    @GetMapping("/complete")
    public String isComplete(ModelMap map, @RequestParam("messageId") int id, @RequestParam("page") String page) {
        messageService.checkComplete(id);
        if (page.equals("c")) return "redirect:/showToComplete";
        if (page.equals("f")) return "redirect:/showFavorites";
        return "redirect:";
    }


    @GetMapping("/find")
    public String findByChar(ModelMap map, @RequestParam("info") String info) {
        map.put("letters", MessageService.letters);

        if (info.equals("all")) return "redirect:";
        Character character = info.charAt(0);
        map.put("status", character);
        List<Message> list = messageService.findByChar(character);

        if (list.isEmpty()) map.put("noRecords", "No Records Found");
        else map.put("messages", list);

        return "index";
    }


    @GetMapping("/findWord")
    public String findByWord(ModelMap map, @RequestParam("word") String word) {
        map.put("letters", MessageService.letters);
        map.put("status", "Search Results");
        List<Message> list = messageService.findByWord(word);

        if (list.isEmpty()) map.put("noRecords", "No Records Found");
        else map.put("messages", list);

        return "index";
    }


    @GetMapping("/findFavorites")
    public String findFavoritesByChar(ModelMap map, @RequestParam("info") Character info) {
        map.put("letters", MessageService.letters);
        map.put("status", info);

        List<Message> list = messageService.findFavoritesByChar(info);
        if (list.isEmpty()) map.put("noRecords", "No Records Found");
        else map.put("messages", list);

        return "index";
    }


    @GetMapping("/findRecord")
    public String findByChar(ModelMap map, @RequestParam("messageId") int id) {
        map.put("letters", MessageService.letters);
        map.put("messages", messageService.findById2(id));
        map.put("status", "Record");
        return "index";
    }


    @GetMapping("/delete")
    public String deleteById(@RequestParam("messageId") int id) {
        messageService.deleteById(id);
        return "redirect:";
    }


    //____________________________________________


    @GetMapping("/showFormForAdd")
    public String showFormForAdd(ModelMap map) {
        Message message = new Message();
        map.put("message", message);
        map.put("letters", MessageService.letters);
        return "form";
    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("messageId") int id, ModelMap map) {
        Message message = messageService.findById(id);
        map.put("message", message);
        map.put("letters", MessageService.letters);
//        map.put("myLetter", message.getCharacter());
        return "form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("message") Message message) {
        messageService.save(message);
        return "redirect:";
    }

}
