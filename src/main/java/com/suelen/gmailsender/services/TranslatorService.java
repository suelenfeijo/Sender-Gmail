package com.suelen.gmailsender.services;


import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.suuft.libretranslate.Language;
import net.suuft.libretranslate.exception.BadTranslatorResponseException;
import net.suuft.libretranslate.type.TranslateResponse;
import net.suuft.libretranslate.util.JsonUtil;

@UtilityClass

public class TranslatorService {
/*TODO: Classe responsável por se comunicar com os serviços de api tradução
 * */
	
	//Link de comunicação com a api (para saber o repositório, ver ApiTranslate.txt
    @Setter
    private String urlApi = "https://translate.fedilab.app/translate";

    
    //definindo o construtor do método, TODO:  @param 1: quem envia , 2 para - 3 : texto a traduzir
    public String translate(@NonNull String from, @NonNull String to, @NonNull String request) {
        try {

        	// URL do serviço Translator
            URL url = new URL(urlApi);
            
         // Realiza a conexão HTTP com a api
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            
         // Configura o método de requisição HTTP como POST
            httpConn.setRequestMethod("POST");

         // Configura os cabeçalhos de requisição
            httpConn.setRequestProperty("accept", "application/json");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
         // Permite que dados sejam enviados para o serviço
            httpConn.setDoOutput(true);
            
         // Escreve os parâmetros de tradução no corpo da requisição
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("q=" + URLEncoder.encode(request, "UTF-8") + "&source=" + from + "&target=" + to + "&format=text");
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();
            
            
         // Verifica se a resposta do serviço é bem-sucedida (código de status 2xx)
            if (!(httpConn.getResponseCode() / 100 == 2)) throw new BadTranslatorResponseException(httpConn.getResponseCode(), urlApi);

            /*
             * o propósito da linha InputStream 
             * responseStream = httpConn.getInputStream(); 
             * é recuperar o fluxo de entrada contendo
             * o corpo da resposta do servidor após uma solicitação
             *  HTTP bem-sucedida.
             */
            InputStream responseStream = httpConn.getInputStream();
            /*
             * Em outras palavras, essa linha permite ler todo o conteúdo do 
             * fluxo de entrada 'responseStream' como uma única string, o que é
             * útil quando se espera receber uma resposta JSON completa de
             * um serviço web.
             */
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            
         /* usa ternário aqui -> hasNext() me diz se tem mais caracteres no fluxo
          * se houver , a condição é true e o método s.next() do Scanner é chamado
          * para obter o próximo elemento
          *  */
            String response = s.hasNext() ? s.next() : "";
            
         // Converte a resposta em um objeto TranslateResponse e retorna o texto traduzido
            return JsonUtil.from(response, TranslateResponse.class).getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace(); //detalhes sobre essas exceções em DiscoveryJAVA.txt
            throw new RuntimeException(e);
        }
    }
    //verificação se existe a variavél no enum , o método recebe-> 1: traduzindo  de , 2: para qual idioma? - 3: texto a traduzir 
    public String translate(@NonNull Language from, @NonNull Language to, @NonNull String request) {
        if (to == Language.NONE || from == to) return request;
        return translate(from.getCode(), to.getCode(), request);
    }
    //para qual linguagem traduzir? texto a traduzir.
    public String translate(@NonNull Language to, @NonNull String request) {
        if (to == Language.NONE) return request;
        return translate("auto", to.getCode(), request);
    }
}
