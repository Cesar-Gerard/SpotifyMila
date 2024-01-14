package model.formatters;

public class FormatTime {

    public static String formatIntToString (int entrada){
        if(entrada>=10){
            String valorFormateado = String.valueOf(entrada);
            return valorFormateado;
        }else {
            String valorFormateado = String.format("%02d", entrada);
            return valorFormateado;
        }

    }

    public static int formatStringToInt(String entrada){
        int valorNumerico = Integer.parseInt(entrada);
        return valorNumerico;
    }
}
