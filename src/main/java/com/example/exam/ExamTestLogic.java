package main.java.com.example.exam;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ExamTestLogic {
    // Attributes
    private ExamTestUI ui; // Tham chiếu đến UI để cập nhật giao diện
    private int index = 0;
    private double score = 0;
    private String status = null;
    private List<Question> listQuestion;
    private LocalDateTime dt; // Giữ lại dt như code gốc

    // Constructor
    public ExamTestLogic(ExamTestUI ui) {
        this.ui = ui;
        listQuestion = new ArrayList<Question>();
        loadQuestionsFromWord("data/questions.docx");
    }

    // Getter và Setter (giữ nguyên như code gốc, trừ setUserName đã chuyển sang UI)
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Question> getListQuestion() {
        return listQuestion;
    }

    // Methods (Logic xử lý)
    
    // Hàm xử lý câu hỏi (giữ nguyên logic tính điểm)
    public void processQuestion(int luaChon) {
        if (index < listQuestion.size()) {
            Question ch = listQuestion.get(index);
            if (luaChon == ch.getIndexRightAns()) {
                // Logic tính điểm: (10 / tổng số câu)
                score += (double) 10 / listQuestion.size(); 
            }
            index++;
            showQuestion(); 
        }
    }

    // Hàm hiển thị câu hỏi (giữ nguyên logic cũ)
    public void showQuestion() {
        if (index < listQuestion.size()) {
            Question ch = listQuestion.get(index);
            ui.displayQuestion(ch); // Gọi UI để hiển thị
        } else {
            // Kết thúc bài thi
            ui.showResult(score); // Gọi UI để hiển thị kết quả và log
        }
    }
    
    // Hàm lấy thời gian (giữ nguyên logic cũ)
    public String upTime() {
        dt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dtfor = dt.format(format);
        return dtfor;
    }

    // Hàm tải câu hỏi từ Word (giữ nguyên logic cũ)
    private void loadQuestionsFromWord(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath); 
             XWPFDocument document = new XWPFDocument(fis)) {
            
            listQuestion.clear();
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            
            for (int i = 0; i < paragraphs.size(); i += 6) {
                if (i + 5 < paragraphs.size()) {
                    String questionText = paragraphs.get(i).getText().trim();
                    String ans1Text = paragraphs.get(i + 1).getText().trim();
                    String ans2Text = paragraphs.get(i + 2).getText().trim();
                    String ans3Text = paragraphs.get(i + 3).getText().trim();
                    String ans4Text = paragraphs.get(i + 4).getText().trim();
                    int correctAnswerIndex = Integer.parseInt(paragraphs.get(i + 5).getText().trim());

                    listQuestion.add(
                            new Question(questionText, ans1Text, ans2Text, ans3Text, ans4Text, correctAnswerIndex));
                }
            }
            System.out.println(upTime() + " Loaded " + listQuestion.size() + " questions from " + filePath);

        } catch (IOException e) {
            System.err.println(upTime() + " Error reading Word file: " + e.getMessage());
            ui.showFileError(filePath, 
                             "Cannot read question file: " + filePath + "\n" + e.getMessage(), 
                             "Error reading file");
        } catch (NumberFormatException e) {
            System.err.println(upTime() + " Error formatting the correct answer in the Word file: " + e.getMessage());
            ui.showFileError(filePath, 
                             "Error formatting the correct answer in the question file. Please check the Word file again.", 
                             "Error formating");
        }
    }
}