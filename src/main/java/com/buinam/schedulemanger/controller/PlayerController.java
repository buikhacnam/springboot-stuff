package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Player;
import com.buinam.schedulemanger.repository.PlayerRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/import-order-excel")
    public List<Player> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {
        List<Player> players = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

        // read player data from excel file sheet 1
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            if (worksheet.getRow(i) != null && worksheet.getRow(i).getCell(0) != null && worksheet.getRow(i).getCell(1) != null) {
                Player player = new Player();
                player.setPlayerName(worksheet.getRow(i).getCell(0).getStringCellValue());
                player.setClub(worksheet.getRow(i).getCell(1).getStringCellValue());
                player.setShirtNumber((int) worksheet.getRow(i).getCell(2).getNumericCellValue());
                players.add(player);
            }
        }

        //save player data to database
        playerRepository.saveAll(players);

        return players;
    }

}
