package com.apu.apstay.staff.manager.controllers.reports.pdfs;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            // Create a new PDF document
            var document = new PDDocument();
            var page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            var boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            var regularFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            
            // Create a content stream for writing to the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            // Add title
            contentStream.beginText();
            contentStream.setFont(boldFont, 14);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Unit Occupancy Report");
            contentStream.endText();
            
            // Add date
            contentStream.beginText();
            contentStream.setFont(regularFont, 12);
            contentStream.newLineAtOffset(50, 730);
            contentStream.showText("Generated: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            contentStream.endText();
            
            // Add summary statistics
            contentStream.beginText();
            contentStream.setFont(boldFont, 12);
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Summary Statistics");
            contentStream.endText();
            
            // Get the same data you use in your view
            // This is where you'd add your actual data retrieval logic
            int totalUnits = 120;
            int occupancyRate = 78;
            int fullyOccupied = 72;
            int vacant = 26;
            
            // Add summary data
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
            contentStream.showText("Fully Occupied Units: " + fullyOccupied + " (" + (fullyOccupied * 100 / totalUnits) + "%)");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 635);
            contentStream.showText("Vacant Units: " + vacant + " (" + (vacant * 100 / totalUnits) + "%)");
            contentStream.endText();
            
            // Create a table for detailed unit data
            float y = 600;
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float rowHeight = 20;
            
            // Fetch your detailed unit data here
            // This is where you'd add your unit data retrieval logic
            
            // Add table header
            drawTableHeader(contentStream, margin, y, tableWidth, boldFont);
            
            // Add table rows
            y -= rowHeight;
            // For each unit in your data...
            String[][] unitData = {
                {"A-101", "1", "2", "2", "100%", "Fully Occupied"},
                {"A-102", "1", "3", "2", "67%", "Partially Occupied"},
                {"A-103", "1", "4", "0", "0%", "Vacant"},
            };
            
            for (String[] row : unitData) {
                drawTableRow(contentStream, margin, y, tableWidth, row, regularFont);
                y -= rowHeight;
                // Check if we need a new page
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
            
            // Close the content stream
            contentStream.close();
            
            // Save the document to response output stream
            document.save(response.getOutputStream());
            document.close();
            
        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().write("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private void drawTableHeader(PDPageContentStream contentStream, float x, float y, float width, PDFont font) throws IOException {
        // Table headers
        String[] headers = {"Unit Name", "Floor", "Capacity", "Current Residents", "Occupancy Rate", "Status"};

        // Calculate cell widths proportionally based on content
        float[] columnWidths = {
            width * 0.15f,  // Unit Name - 15%
            width * 0.10f,  // Floor - 10%
            width * 0.12f,  // Capacity - 12%
            width * 0.20f,  // Current Residents - 20%
            width * 0.18f,  // Occupancy Rate - 18%
            width * 0.25f   // Status - 25%
        };

        contentStream.setFont(font, 10);

        float xPosition = x;
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, y - 15);
            contentStream.showText(headers[i]);
            contentStream.endText();

            // Move to next column position
            xPosition += columnWidths[i];
        }

        // Draw table lines
        contentStream.setLineWidth(0.5f);

        // Draw horizontal lines (top and bottom of header)
        contentStream.moveTo(x, y);
        contentStream.lineTo(x + width, y);
        contentStream.stroke();

        contentStream.moveTo(x, y - 20);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();

        // Draw vertical lines between columns
        xPosition = x;
        for (int i = 0; i < columnWidths.length; i++) {
            contentStream.moveTo(xPosition, y);
            contentStream.lineTo(xPosition, y - 20);
            contentStream.stroke();
            xPosition += columnWidths[i];
        }

        // Draw final vertical line
        contentStream.moveTo(x + width, y);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();
    }
    
    private void drawTableRow(PDPageContentStream contentStream, float x, float y, float width, String[] rowData, PDFont font) throws IOException {
        // Calculate cell widths proportionally based on content (same as header)
        float[] columnWidths = {
            width * 0.15f,  // Unit Name - 15%
            width * 0.10f,  // Floor - 10%
            width * 0.12f,  // Capacity - 12%
            width * 0.20f,  // Current Residents - 20%
            width * 0.18f,  // Occupancy Rate - 18%
            width * 0.25f   // Status - 25%
        };

        contentStream.setFont(font, 10);

        float xPosition = x;
        for (int i = 0; i < rowData.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + 5, y - 15);
            contentStream.showText(rowData[i]);
            contentStream.endText();

            // Move to next column position
            xPosition += columnWidths[i];
        }

        // Draw horizontal line at bottom of row
        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(x, y - 20);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();

        // Draw vertical lines between columns
        xPosition = x;
        for (int i = 0; i < columnWidths.length; i++) {
            contentStream.moveTo(xPosition, y);
            contentStream.lineTo(xPosition, y - 20);
            contentStream.stroke();
            xPosition += columnWidths[i];
        }

        // Draw final vertical line
        contentStream.moveTo(x + width, y);
        contentStream.lineTo(x + width, y - 20);
        contentStream.stroke();
    }
    // </editor-fold>
}
