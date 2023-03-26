package TM01;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class myFrame extends JFrame {
    private JButton btnSubmit;
    private JPanel panelMain;
    private JList<String> list;

    public myFrame() {
        super("MENCARI DATA BERAWALAN HURUF S DAN HARGA < 7000");
        this.setContentPane(panelMain);
        setSize(400, 300);
        setLayout(new BorderLayout());

        btnSubmit = new JButton("Tampilkan Data!");
        add(btnSubmit, BorderLayout.NORTH);

        list = new JList<>();

        add(new JScrollPane(list), BorderLayout.CENTER);

        btnSubmit.addActionListener(e -> {
            try {
                ArrayList<ResponseModel> responseModel = getData();
                DefaultListModel<String> model = new DefaultListModel<>();
                for (ResponseModel responseModel1 : responseModel) {
                    model.addElement(String.valueOf(responseModel1));
                }
                list.setModel(model);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private ArrayList<ResponseModel> getData() throws IOException {
        // Mendapatkan data dari sumber data (dalam hal ini hard-coded)
        ConnURI koneksiSaya = new ConnURI();
        URL myAddres = koneksiSaya.buildURL("https://farmasi.mimoapps.xyz/mimoqss2auyqD1EAlkgZCOhiffSsFl6QqAEIGtM");
        String response = koneksiSaya.getResponseFromHttpUrl(myAddres);
        System.out.println(response);
        JSONArray responseJSON = new JSONArray(response);
        ArrayList<ResponseModel> responseModel = new ArrayList<>();
        for (int i = 0; i < responseJSON.length(); i++){
            JSONObject myJSONObject = responseJSON.getJSONObject(i);
            String name = myJSONObject.getString("i_name");
            int harga = Integer.parseInt(myJSONObject.getString("i_sell"));
            if(name.startsWith("S") && harga <= 7000){
                ResponseModel resModel = new ResponseModel();
            resModel.setI_name(myJSONObject.getString("i_name"));
            resModel.setI_sell(myJSONObject.getString("i_sell"));
            resModel.setI_qty(myJSONObject.getString("i_qty"));
            responseModel.add(resModel);
            }
        }

        DefaultListModel<String> model = new DefaultListModel<>();
        for (ResponseModel resModel : responseModel) {
            model.addElement(String.format("Nama Barang: " + resModel.getI_name()
                    + "\nHarga Barang: " + resModel.getI_sell() + "\nStok Saat Ini: " + resModel.getI_qty()));
        }
        return responseModel;
    }
    public static void main(String[] args) {
        myFrame frame = new myFrame();
        frame.setVisible(true);}
}