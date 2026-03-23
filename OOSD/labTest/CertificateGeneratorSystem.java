package labTest;

import java.util.ArrayList;

interface Certificate {
    String getTemplateName();

    double getBaseSize();
}

class CompletionCert implements Certificate {

    @Override
    public String getTemplateName() {
        return "CompletionCert";
    }

    @Override
    public double getBaseSize() {
        return 150.0;
    }
}

class AchievementCert implements Certificate {

    @Override
    public String getTemplateName() {
        return "AchievementCert";
    }

    @Override
    public double getBaseSize() {
        return 200.0;
    }
}

class ParticipationCert implements Certificate {

    @Override
    public String getTemplateName() {
        return "ParticipationCert";
    }

    @Override
    public double getBaseSize() {
        return 120.0;
    }
}

class FactoryTemplate {

    public static Certificate create(String type) {
        if (type.equals("CompletionCert")) {
            return new CompletionCert();
        } else if (type.equals("AchievementCert")) {
            return new AchievementCert();
        } else if (type.equals("ParticipationCert")) {
            return new ParticipationCert();
        }
        throw new IllegalArgumentException("Unknow");
    }
}

abstract class DecoratorCertificate implements Certificate {
    private Certificate certificate;

    public DecoratorCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public String getTemplateName() {
        return certificate.getTemplateName();
    }

    @Override
    public double getBaseSize() {
        return certificate.getBaseSize();
    }
}

class Watermark extends DecoratorCertificate {

    public Watermark(Certificate certificate) {
        super(certificate);
    }

    @Override
    public String getTemplateName() {
        return super.getTemplateName() + " + Watermark";
    }

    @Override
    public double getBaseSize() {
        return super.getBaseSize() + 50.0;
    }
}

class DigitalSignature extends DecoratorCertificate {

    public DigitalSignature(Certificate certificate) {
        super(certificate);
    }

    @Override
    public String getTemplateName() {
        return super.getTemplateName() + " + Digital Signature";
    }

    @Override
    public double getBaseSize() {
        return super.getBaseSize() + 30.0;
    }
}

class CustomBorder extends DecoratorCertificate {

    public CustomBorder(Certificate certificate) {
        super(certificate);
    }

    @Override
    public String getTemplateName() {
        return super.getTemplateName() + " + Custom Border";
    }

    @Override
    public double getBaseSize() {
        return super.getBaseSize() + 80.0;
    }
}

interface ExportStrategy {
    double calculateFinalSize(double totalSize);

    String getExportFormat();
}

class PDFExport implements ExportStrategy {

    @Override
    public double calculateFinalSize(double totalSize) {
        return totalSize * 1.0;
    }

    @Override
    public String getExportFormat() {
        return "PDF";
    }
}

class JpgExport implements ExportStrategy {

    @Override
    public double calculateFinalSize(double totalSize) {
        return totalSize * 0.6;
    }

    @Override
    public String getExportFormat() {
        return "JPG (Compressed)";
    }
}

class PngExport implements ExportStrategy {

    @Override
    public double calculateFinalSize(double totalSize) {
        return totalSize * 1.2;
    }

    @Override
    public String getExportFormat() {
        return "PNG";
    }
}

class ContextStrategy {
    private ExportStrategy exportStrategy;

    public void setStrategyExport(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public ExportStrategy getExport() {
        return this.exportStrategy;
    }

    public double calculate(double total) {
        return exportStrategy.calculateFinalSize(total);
    }
}

class CertItem {
    private Certificate certificate;
    private String studentName;

    public CertItem(Certificate certificate, String studentName){
        this.certificate = certificate;
        this.studentName = studentName;
    }

    public double getFileSize(){
        return certificate.getBaseSize();
    }

    public String getStudentName(){
        return studentName;
    }

    public String getCertificate(){
        return certificate.getTemplateName();
    }

}

class BatchJob{
    private ArrayList<CertItem> certItems;
    private ExportStrategy exportStrategy;

    public BatchJob(){
        this.certItems = new ArrayList<>();
    }

    public void addItemcerti(Certificate certificate, String studentName){  
        certItems.add(new CertItem(certificate, studentName));
    }

    public double getTotalBatchSize(){
        double size = 0;
        for (int i = 0; i < certItems.size(); i++) {
            size += certItems.get(i).getFileSize();
        }
        return size;
    }

    public double getFinalStorageNeeded(){
        return exportStrategy.calculateFinalSize(getTotalBatchSize());
    }

    public ArrayList<CertItem> getCertItem(){
        return certItems;
    }

    public ExportStrategy getExportStrategy(){
        return exportStrategy;
    }

    public void setExport(ExportStrategy exportStrategy){
        this.exportStrategy = exportStrategy;
    }
}

class DashboardView {

    public void displayJobSummary(BatchJob batchJob){
        System.out.println("===== QuickCert: Batch Job Summary =====");
        for (int i = 0; i < batchJob.getCertItem().size(); i++) {
            System.out.println((i + 1) + ". [" + batchJob.getCertItem().get(i).getStudentName() + "] " + batchJob.getCertItem().get(i).getCertificate() + " | " + batchJob.getCertItem().get(i).getFileSize() + " KB");
        }
        System.out.println("--------------------------------------");
        System.out.println("Start total file size: " + batchJob.getTotalBatchSize() + " KB");
        System.out.println("Pattern is Export: " + batchJob.getExportStrategy().getExportFormat());
        System.out.println("Want store area is: " + batchJob.getFinalStorageNeeded());
        System.out.println("======================================");
    }

    public void displayLog(BatchJob job){
        System.out.println("===== System Log =====");
        System.out.println("Successfully exported " + job.getCertItem().size() + " certificates.");
        System.out.println("======================");
    }
}

class CertController{
    private BatchJob model;
    private DashboardView view;

    public CertController(){
        model = new BatchJob();
        view = new DashboardView();
    }

    public void addCertificate(String type, String[] features, String studentName){
        Certificate certificate = FactoryTemplate.create("CompletionCert");
        Certificate certificate2 = FactoryTemplate.create("AchievementCert");
        for (int i = 0; i < features.length; i++) {
            if(features[i].equals("DigitalSignature")){
                certificate = new DigitalSignature(certificate);
                certificate = new DigitalSignature(certificate2);
            }
            else if(features[i].equals("Watermark")){
                certificate = new Watermark(certificate);
                certificate2 = new Watermark(certificate2);
            }
            else if(features[i].equals("CustomBorder")){
                certificate = new CustomBorder(certificate);
                certificate2 = new CustomBorder(certificate2);
            }
        }
        model.addItemcerti(certificate, studentName);
    }

    public void setExportFormat(ExportStrategy strategy){
        model.setExport(strategy);
    }

    public void processBatch(){
        view.displayJobSummary(model);
        view.displayLog(model);
    }
}

public class CertificateGeneratorSystem {
    public static void main(String[] args) {

        Certificate c = FactoryTemplate.create("CompletionCert");
        Certificate c2 = FactoryTemplate.create("AchievementCert");
        Certificate c3 = FactoryTemplate.create("ParticipationCert");

        System.out.println("Template: " + c.getTemplateName() + " | " + "Base Size: " + c.getBaseSize() + " KB");
        System.out.println("Template: " + c2.getTemplateName() + " | " + "Base Size: " + c2.getBaseSize() + " KB");
        System.out.println("Template: " + c3.getTemplateName() + " | " + "Base Size: " + c3.getBaseSize() + " KB");
        System.out.println();

        c3 = new DigitalSignature(c3);
        c2 = new CustomBorder(c2);
        c2 = new Watermark(c2);
        System.out.println("Document: " + c3.getTemplateName() + " | Size: " + c3.getBaseSize());
        System.out.println("Document: " + c2.getTemplateName() + " | Size: " + c2.getBaseSize());
        System.out.println();

        ContextStrategy contextStrategy = new ContextStrategy();
        Certificate c4 = FactoryTemplate.create("CompletionCert");
        contextStrategy.setStrategyExport(new JpgExport());

        System.out.println("Document: " + c4.getTemplateName() + " | " + "Start size: " + c4.getBaseSize() + "KB");
        System.out.println("Export is " + contextStrategy.getExport().getExportFormat() + " Total file size: " + contextStrategy.getExport().calculateFinalSize(c4.getBaseSize()));
        System.out.println();

        Certificate c5 = FactoryTemplate.create("AchievementCert");
        c5 = new DigitalSignature(c5);
        contextStrategy.setStrategyExport(new PngExport());
        contextStrategy.calculate(c5.getBaseSize());

        System.out.println("Document: " + c5.getTemplateName() + " | " + "Start size: " + c5.getBaseSize() + "KB");
        System.out.println("Export is " + contextStrategy.getExport().getExportFormat() + " Total file size: " + contextStrategy.getExport().calculateFinalSize(c5.getBaseSize()));
        System.out.println();


        CertController certController = new CertController();
        certController.addCertificate("CompletionCert", new String[]{"Watermark"}, "John Doe");
        certController.addCertificate("AchievementCert", new String[]{"DigitalSignature","CustomBorder"}, "Jane Smith");
        
        certController.setExportFormat(new PDFExport());
        certController.processBatch();
    }
}
