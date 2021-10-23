package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
This class provides opportunity to send requests to WolframAlpha and get results in a form
of expressions.

WARNING!
1. WolframAlpha is free only for student purposes. This particular project is based on student
license which provides 2000 free requests per month.
2. There are restrictions on functions that are recognized by the Shunting-yard algorithm.
It is possible that Wolfram will return unrecognizable result. If it is so, unfortunately,
user will have to solve equation by himself and use the standard input.
 */

public class WolframAlphaCalculator {
    private static final String APP_ID = "***";

    // Running WolframAlpha on inputted differential equation
    public void ODESolution(String equation, Controller_Graphs cg) throws Exception {
        String inputEquation = "y'=" + equation.toLowerCase();

        // Sending request for obtaining solution of y
        String requestURL = null;
        try {
            requestURL = "http://api.wolframalpha.com/v2/query?appid=" + APP_ID + "&input=" + URLEncoder.encode(inputEquation, StandardCharsets.UTF_8.toString()) + "&format=plaintext&output=JSON";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String response = getURLResult(requestURL);
        //System.out.println(response);

        JSONObject json = new JSONObject(response);
        if (!json.getJSONObject("queryresult").getBoolean("success")){
            throw new MalformedExpressionException();
        }

        /*
        Parsing json response and converting result in to the appropriate for
        Shunting-yard algorithm format.
        */

        JSONArray jsonArray = json.getJSONObject("queryresult").getJSONArray("pods");
        String exactSolution = null;
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getString("title").equals("Differential equation solution") &&
                    !jsonObject.getString("title").equals("Differential equation solutions")){
                continue;
            }
            exactSolution = ((JSONObject) (jsonObject.getJSONArray("subpods").get(0))).getString("plaintext");
            break;
        }
        if (exactSolution == null){
            throw new WolframAlphaException();
        }
        exactSolution = exactSolution.substring(exactSolution.indexOf("=") + 1).replaceAll("log", "ln");
        if (exactSolution.contains("and")){
            exactSolution = exactSolution.substring(0, exactSolution.indexOf("and"));
        }
        while (exactSolution.contains("c_")){
            int index = exactSolution.indexOf("c_");
            int end = index + 2;
            while (Character.isDigit(exactSolution.charAt(end))){
                end++;
            }
            exactSolution = exactSolution.substring(0, index) + "c" + exactSolution.substring(end);
        }

        // Sending request for obtaining solution of C based on the solution of y

        String findC = "find c y="+exactSolution;
        try {
            requestURL = "http://api.wolframalpha.com/v2/query?appid=" + APP_ID + "&input=" + URLEncoder.encode(findC, StandardCharsets.UTF_8.toString()) + "&format=plaintext&output=JSON";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        response = getURLResult(requestURL);
        // System.out.println(response);

        json = new JSONObject(response);
        if (!json.getJSONObject("queryresult").getBoolean("success")){
            throw new MalformedExpressionException();
        }

        /*
        Parsing json response and converting result in to the appropriate for
        Shunting-yard algorithm format.
        */

        jsonArray = json.getJSONObject("queryresult").getJSONArray("pods");
        String CSolution = null;
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getString("title").equals("Result") && !jsonObject.getString("title").equals("Results")){
                continue;
            }
            CSolution = ((JSONObject) (jsonObject.getJSONArray("subpods").get(0))).getString("plaintext");
            break;
        }
        if (CSolution == null){
            throw new WolframAlphaException();
        }
        CSolution = CSolution.substring(CSolution.indexOf("=") + 1).replaceAll("log", "ln");
        if (CSolution.contains("and")){
            CSolution = CSolution.substring(0, CSolution.indexOf("and"));
        }

        if (CSolution.contains("π")){
            int piIndex = CSolution.indexOf("π");
            int start = piIndex;
            while (CSolution.charAt(start) != '+') start--;
            String strEnd = CSolution.substring(start);
            int parCount = 0;
            while (strEnd.contains(")")){
                strEnd = strEnd.replace(")", "o");
                parCount++;
            }
            CSolution = CSolution.substring(0, start);
            while (parCount>0){
                int st = 0;
                while (CSolution.charAt(st)!='('){
                    st++;
                }
                CSolution = CSolution.substring(st+1);
                parCount--;
            }
        }

        // Converting some functions into readable form

        exactSolution = exactSolution.replaceAll("sin\\^(-1)", "asin");
        exactSolution = exactSolution.replaceAll("cos\\^(-1)", "acos");
        exactSolution = exactSolution.replaceAll("tg\\^(-1)", "atan");

        CSolution = CSolution.replaceAll("sin\\^(-1)", "asin");
        CSolution = CSolution.replaceAll("cos\\^(-1)", "acos");
        CSolution = CSolution.replaceAll("tg\\^(-1)", "atan");

        System.out.println(exactSolution);
        System.out.println(CSolution);

        // Finally, initializing Controller_Graphs instance
        cg.setFunctions(new Function(equation),
                new Function(exactSolution),
                new Function(CSolution));
    }

    // Sending request
    private String getURLResult(String url) throws WolframAlphaException {
        String requestURL = url;
        URL request;
        Scanner scanner;
        try {
            request = new URL(requestURL);
            scanner = new Scanner(request.openStream());
        } catch (IOException e) {
            throw new WolframAlphaException();
        }
        String res = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return res;
    }
}
