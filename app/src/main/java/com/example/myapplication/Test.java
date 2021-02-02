package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.registerScreens.RegisterScreen;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test extends AppCompatActivity {

    WebView webView;
    EditText editTest;
    int pageIndex = 0;
    String url = "https://www.sepe.gov.ao/ao/catalogo/eservicos/consulta-de-nif/";

    int toques = 0;
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            Toast.makeText(this, "can go back", Toast.LENGTH_SHORT).show();
            webView.evaluateJavascript(
                    "(function() {return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                            webView.setVisibility(View.GONE);
                            int nifIndex = html.indexOf("NIF:");
                            int nomeindex = html.indexOf("Nome:");
                            int tipoIndex = html.indexOf("Tipo:");
                            int cont = html.indexOf("Tipo Contribuinte:");
                            int estad = html.indexOf("Estado:");
                            int rep = html.indexOf("Reparticao:");


                            String nome = html.substring(nomeindex,tipoIndex);
                            String nif = html.substring(nifIndex,nomeindex);

                            int outro = nome.indexOf(">");
                            int ult = nome.indexOf("\\u003C/p>");
                            ++outro;

                            int outro2 = nif.indexOf(">");
                            int ult2 = nif.indexOf("\\u003C/p>");
                            ++outro2;
                            try {
                                String ver = nome.substring(outro,ult);
                                String ne = nif.substring(outro2,ult2);
                                editTest.setText(ne);
                            }catch (IllegalStateException ex){
                                Toast.makeText(Test.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }catch (IndexOutOfBoundsException ex){
                                editTest.setText(nif);
                                Toast.makeText(Test.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        }
        else {


            /*
            webView.evaluateJavascript(
                    "(function() {document.getElementsByTagName('header')[0].style.display='none';  return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                        }
                    });

             */

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editTest = findViewById(R.id.editTest);
        webView = (WebView) findViewById(R.id.webView2);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               if (webView.canGoBack()){
                   Toast.makeText(Test.this, "pode ir 1", Toast.LENGTH_SHORT).show();
               }
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                ++pageIndex;
                /*if (webView.canGoBack()){
                    Toast.makeText(Test.this, "pode ir 2", Toast.LENGTH_SHORT).show();
                }

                 */
                Toast.makeText(Test.this, "\\u003", Toast.LENGTH_SHORT).show();
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('header')[0].style.display='none';" +
                        "document.getElementsByTagName('header')[1].style.display='none';" +
                        "document.getElementsByTagName('footer')[0].style.display='none';" +
                        "document.getElementsByTagName('body')[0].style.marginTop='0px';" +
                        "document.getElementsByTagName('span')[0].style.display='none';" +
                        "document.getElementsByClassName('text-center')[1].style.display='none';" +
                        "document.getElementsByClassName('linha_separadora')[0].style.display='none';" +
                        "document.getElementsByClassName('linha_separadora')[1].style.display='none';" +
                        "document.getElementsByClassName('linha_separadora')[2].style.display='none';" +
                        "document.getElementsByClassName('voltar')[1].style.display='none';" +
                        "document.getElementsByClassName('informacao_foi_util row')[0].style.display='none';" +
                        "document.getElementsByClassName('sociais')[0].style.display='none';" +
                        "})()");
            };

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Toast.makeText(Test.this, "trocou", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        webView.loadUrl(url);


      /*
        webView.addJavascriptInterface(new MyWebAppInterface(Test.this), "HtmlViewer");


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                webView.evaluateJavascript(
                        "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                webView.setVisibility(View.GONE);
                                // code here
                            }
                        });


                webView.evaluateJavascript(
                        "(function() { " +
                                "var element = document.getElementsByTagName('html')[0];\n" +
                                "\n" +
                                "// create an observer instance\n" +
                                "var observer = new MutationObserver(function(mutations) {\n" +
                                "  alert(\"Some changes were made\");\n" +
                                "});\n" +
                                "\n" +
                                "// configuration of the observer:\n" +
                                "var config = { childList: true};" +
                                "\n" +
                                "" +
                                "observer.observe(element, config);" +
                                "return alert(\"Some changes were made\");" +
                                "" +
                                " })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                // code here
                            }
                        });

                webView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");


            }
        });

       */
        //webView.loadUrl(url);


    }
    public class MyWebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        MyWebAppInterface(Context c) {
            this.mContext = c;
        }


        /** Show a toast from the web page */
        @JavascriptInterface
        public void showHtml(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }


}
