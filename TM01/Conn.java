package TM01;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
public class Conn {
    public void displayData() throws IOException {
        ConnURI koneksiSaya = new ConnURI();
        URL myAddres = koneksiSaya.buildURL("https://farmasi.mimoapps.xyz/mimoqss2auyqD1EAlkgZCOhiffSsFl6QqAEIGtM");
        String response = koneksiSaya.getResponseFromHttpUrl(myAddres);
        System.out.println(response);
//decode
        assert response != null;
        JSONArray responseJSON = new JSONArray(response);
        ArrayList<ResponseModel> responseModel = new ArrayList<>();
        for (int i = 0; i < responseJSON.length(); i++) {
            ResponseModel resModel = new ResponseModel();
            JSONObject myJSONObject = responseJSON.getJSONObject(i);
            resModel.setI_name(myJSONObject.getString("i_name"));
            resModel.setI_qty(myJSONObject.getString("i_qty"));
            resModel.setI_sell(myJSONObject.getString("i_sell"));
            responseModel.add(resModel);
        }
        for (int i = 0; i < responseModel.size(); i++) {
            String name = responseModel.get(i).getI_name();
            String toUpperCase = name.toUpperCase();
            int harga = Integer.parseInt(responseModel.get(i).getI_sell());
            Long stok = Long.parseLong(responseModel.get(i).getI_qty());
            if (name.startsWith("S") && harga <= 7000) {
                System.out.println("Nama Barang : " + toUpperCase +
                        "\nHarga Barang : " + harga + "\nStok Saat Ini : " + stok + "\n");
            }
        }
    }
    public static void main(String[] args) throws IOException {}
}
