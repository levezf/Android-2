package com.example.felipelevez.aprendizadoandroid_listadeprodutos.utils;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;

public class EditTextUtils {

    public static boolean emailEhValido(Context context, EditText et){
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches()){
            return true;
        }else{
            msgErro(et, context.getString(R.string.msg_email_invalido));
            return false;
        }
    }

    public static boolean phoneEhValido(Context context, EditText et){
        if(Patterns.PHONE.matcher(et.getText().toString()).matches() && et.getText().toString().length()>=10){
            return true;
        }else{
            msgErro(et, context.getString(R.string.msg_telefone_invalido));
            return false;
        }
    }

    public static boolean cnpjEhValido(Context context, EditText et){
        String cnpj = et.getText().toString();
        int[] multiplicador1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] multiplicador2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
        int soma;
        int resto;
        String digito;
        String tempCnpj;
        cnpj = cnpj.trim();
        cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "");
        if (cnpj.length() != 14) {
            msgErro(et, context.getResources().getString(R.string.msg_cnpj_invalido));
            return false;
        }
        tempCnpj = cnpj.substring(0, 12);
        soma = 0;
        for(int i=0; i<12; i++)
            soma += Integer.parseInt(String.valueOf(tempCnpj.charAt(i))) * multiplicador1[i];
        resto = (soma % 11);
        if ( resto < 2)
            resto = 0;
        else
            resto = 11 - resto;
        digito = String.valueOf(resto);
        tempCnpj = tempCnpj + digito;
        soma = 0;
        for (int i = 0; i < 13; i++)
            soma += Integer.parseInt(String.valueOf(tempCnpj.charAt(i))) * multiplicador2[i];
        resto = (soma % 11);
        if (resto < 2)
            resto = 0;
        else
            resto = 11 - resto;
        digito = digito + String.valueOf(resto);

        if(!cnpj.endsWith(digito)){
            msgErro(et, context.getResources().getString(R.string.msg_cnpj_invalido));
            return false;
        }else{
            return true;
        }

    }

    private static void msgErro(EditText et, String mError) {
        et.setError(mError);
    }

}