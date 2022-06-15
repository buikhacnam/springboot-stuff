package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Player;
import com.buinam.schedulemanger.repository.PlayerRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/import-order-excel")
    public List<Player> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {
        List<Player> players = new ArrayList<>();

        List<Player> existingPlayers = playerRepository.findAll();

        List<String> names = new ArrayList<>();
        List<String> clubs = new ArrayList<>();
        existingPlayers.forEach(player -> {
            names.add(player.getPlayerName());
            clubs.add(player.getClub());
        });



        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

        // read player data from excel file sheet 1
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            if (worksheet.getRow(i) != null && worksheet.getRow(i).getCell(0) != null && worksheet.getRow(i).getCell(1) != null) {
                String name = worksheet.getRow(i).getCell(0).getStringCellValue();
                String club = worksheet.getRow(i).getCell(1).getStringCellValue();
                int shirtNumber = (int) worksheet.getRow(i).getCell(2).getNumericCellValue();

                if (!names.contains(name) || !clubs.contains(club)) {
                    Player player = new Player();
                    player.setPlayerName(name);
                    player.setClub(club);
                    player.setShirtNumber(shirtNumber);
                    players.add(player);
                }
            }
        }

        System.out.println("players: " + players.toString());

        //save player data to database
        playerRepository.saveAll(players);

        return players;
    }

    @GetMapping("/export-order-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Player> players = playerRepository.findAll();
        PlayerExcelExporter playerExcelExporter = new PlayerExcelExporter(players);
        playerExcelExporter.export(response);
    }

}

class PlayerExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet worksheet;
    private List<Player> players;

    public PlayerExcelExporter(List<Player> players) {
        this.players = players;
        this.workbook = new XSSFWorkbook();
        this.worksheet = workbook.createSheet("Players");
    }

    private void writeHeaderLine() {
        Row headerRow = worksheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(headerRow, 0, "Player Name", style);
        createCell(headerRow, 1, "Club", style);
        createCell(headerRow, 2, "Shirt Number", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        worksheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Player player : players) {
            Row row = worksheet.createRow(rowCount++);
            createCell(row, 0, player.getPlayerName(), style);
            createCell(row, 1, player.getClub(), style);
            createCell(row, 2, player.getShirtNumber(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

//        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=players_" + currentDateTime + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();
    }
}
