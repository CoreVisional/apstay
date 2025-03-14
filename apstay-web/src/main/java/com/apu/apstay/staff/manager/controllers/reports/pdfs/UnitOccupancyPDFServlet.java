package com.apu.apstay.staff.manager.controllers.reports.pdfs;

import com.apu.apstay.services.UnitService;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

/**
 *
 * @author alexc
 */
@WebServlet(name = "UnitOccupancyPDFServlet", urlPatterns = {"/manager/reports/unit-occupancy/pdf"})
public class UnitOccupancyPDFServlet extends HttpServlet {

    @EJB
    private UnitService unitService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set the response type
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=unit-occupancy-report.pdf");
        
        try {
            Map<String, Object> reportData = unitService.getUnitOccupancyReportData();

            int totalUnits = (int) reportData.get("totalUnits");
            int activeUnits = (int) reportData.get("activeUnits");
            int inactiveUnits = (int) reportData.get("inactiveUnits");
            int fullyOccupiedUnits = (int) reportData.get("fullyOccupiedUnits");
            int vacantUnits = (int) reportData.get("vacantUnits");

            int occupiedUnits = activeUnits - vacantUnits;
            int occupancyRate = totalUnits > 0 ? (occupiedUnits * 100 / totalUnits) : 0;

            var document = new PDDocument();
            var page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            var boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            var regularFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(boldFont, 14);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Unit Occupancy Report");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(regularFont, 12);
            contentStream.newLineAtOffset(50, 730);
            contentStream.showText("Generated: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(boldFont, 12);
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Summary Statistics");
            contentStream.endText();

            contentStream.setFont(regularFont, 10);
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 680);
            contentStream.showText("Total Units: " + totalUnits);
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 665);
            contentStream.showText("Overall Occupancy Rate: " + occupancyRate + "%");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 650);
            int fullyOccupiedPercent = totalUnits > 0 ? (fullyOccupiedUnits * 100 / totalUnits) : 0;
            contentStream.showText("Fully Occupied Units: " + fullyOccupiedUnits + " (" + fullyOccupiedPercent + "%)");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 635);
            int vacantPercent = totalUnits > 0 ? (vacantUnits * 100 / totalUnits) : 0;
            contentStream.showText("Vacant Units: " + vacantUnits + " (" + vacantPercent + "%)");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 620);
            contentStream.showText("Active Units: " + activeUnits);
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 605);
            contentStream.showText("Inactive Units: " + inactiveUnits);
            contentStream.endText();

            float y = 575;
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float rowHeight = 20;

            drawTableHeader(contentStream, margin, y, tableWidth, boldFont);

            List<String[]> unitData = unitService.getUnitDetailsForPdfReport();

            y -= rowHeight;
            for (String[] row : unitData) {
                drawTableRow(contentStream, margin, y, tableWidth, row, regularFont);
                y -= rowHeight;
                if (y <= 100) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    y = 750;
                    drawTableHeader(contentStream, margin, y, tableWidth, boldFont);
                    y -= rowHeight;
                }
            }

            contentStream.close();

            document.save(response.getOutputStream());
            document.close();
            
        } catch (IOException e) {
            response.setContentType("text/html");
            response.getWriter().write("Error generating PDF: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Unit Occupancy Report PDF Generator";
    }
    
    private void drawTableHeader(PDPageContentStream contentStream, float x, float y, float width, PDFont font) throws IOException {

        String[] headers = {"Unit Name", "Floor", "Capacity", "Current Residents", "Occupancy Rate", "Status"};

        float[] columnWidths = {
            width * 0.15f,  // Unit Name
            width * 0.10f,  // Floor
            width * 0.12f,  // Capacity
            width * 0.20f,  // Current Residents
            width * 0.18f,  // Occupancy Rate
            width * 0.25f   // Status
        };

        contentStream.setFont(font, 10);

        float xPosition = x;
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, y - 15);
            contentStream.showText(headers[i]);
            contentStream.endText();

            xPosition += columnWidths[i];
        }

        contentStream.setLineWidth(0.5f);

        contentStream.moveTo(x, y);
        contentStream.lineTo(x + width, y);
        contentStream.stroke();

        contentStream.moveTo(x, y - 20);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();

        xPosition = x;
        for (int i = 0; i < columnWidths.length; i++) {
            contentStream.moveTo(xPosition, y);
            contentStream.lineTo(xPosition, y - 20);
            contentStream.stroke();
            xPosition += columnWidths[i];
        }

        contentStream.moveTo(x + width, y);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();
    }
    
    private void drawTableRow(PDPageContentStream contentStream, float x, float y, float width, String[] rowData, PDFont font) throws IOException {

        float[] columnWidths = {
            width * 0.15f,  // Unit Name
            width * 0.10f,  // Floor
            width * 0.12f,  // Capacity
            width * 0.20f,  // Current Residents
            width * 0.18f,  // Occupancy Rate
            width * 0.25f   // Status
        };

        contentStream.setFont(font, 10);

        float xPosition = x;
        for (int i = 0; i < rowData.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, y - 15);
            contentStream.showText(rowData[i]);
            contentStream.endText();

            xPosition += columnWidths[i];
        }

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(x, y - 20);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();

        xPosition = x;
        for (int i = 0; i < columnWidths.length; i++) {
            contentStream.moveTo(xPosition, y);
            contentStream.lineTo(xPosition, y - 20);
            contentStream.stroke();
            xPosition += columnWidths[i];
        }

        contentStream.moveTo(x + width, y);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();
    }
    // </editor-fold>
}
