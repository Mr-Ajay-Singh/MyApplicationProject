package com.overall.myApplicationProject.mainPage.component

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.MailTo
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.overall.MyApplicationProject.R

@Composable
fun MainTabUi() {

    val activity = LocalContext.current as Activity
    val isLoading = remember { mutableStateOf(true) }
    val loadUrl = "https://www.google.com"

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.white),
                RoundedCornerShape(0.dp)
            )
            .padding(vertical = 0.dp, horizontal = 16.dp)
    ) {
        AndroidView(
            factory = {
                WebView(it).apply {

                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    val userAgentFake =
                        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"

                    run {
                        // WebView
                        this.webChromeClient = WebChromeClient()
                        this.webViewClient = MyWebViewClient(activity,this,isLoading)

                        val settings = this.settings
                        settings.setSupportZoom(true)
                        settings.mediaPlaybackRequiresUserGesture = true
                        settings.builtInZoomControls = true
                        settings.displayZoomControls = false
                        settings.allowFileAccess = true
                        settings.allowContentAccess = true
                        settings.loadWithOverviewMode = true
                        settings.saveFormData = true
                        settings.useWideViewPort = false
                        settings.setSupportMultipleWindows(false)
                        settings.loadsImagesAutomatically = true
                        settings.blockNetworkImage = false
                        settings.blockNetworkLoads = false
                        settings.javaScriptEnabled = true
                        settings.allowUniversalAccessFromFileURLs = true
                        settings.allowFileAccessFromFileURLs = true
                        settings.databaseEnabled = true
                        settings.domStorageEnabled = true
                        settings.setGeolocationEnabled(true)
                        settings.javaScriptCanOpenWindowsAutomatically = true
                        //settings.userAgentString = userAgentFake
                        settings.setNeedInitialFocus(true)
                        settings.offscreenPreRaster = true
                        if (loadUrl.isNotEmpty()) {
                            this.loadUrl(loadUrl)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )


        if(isLoading.value){
            Icon(
                painter = painterResource(id = R.drawable.ic_undo),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(-angle)
                    .align(Alignment.Center)
            )
        }

    }
}


private class MyWebViewClient(val context: Context, val webView: WebView, val isLoading: MutableState<Boolean>) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        isLoading.value = true
        url?.let {
            if (!url.contains("docs.google.com") && url.endsWith(".pdf")) {
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=$url")
            }
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        isLoading.value = false
        try {
            view?.loadUrl(
                "javascript:$(document).ajaxStart(function (event, request, settings) { " +
                        "ajaxHandler.ajaxBegin(); " +  // Event called when an AJAX call begins
                        "});"
            )
            view?.loadUrl(
                "javascript:$(document).ajaxComplete(function (event, request, settings) { " +
                        "ajaxHandler.ajaxDone(); " +  // Event called when an AJAX call ends
                        "});"
            )
        } catch (e: Exception) {
            Log.d("Error",e.toString())
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (!url.isNullOrEmpty()) {
            return if (url.endsWith(".mp4")) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(url), "video/*")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view?.context?.startActivity(intent)
                // If we return true, onPageStarted, onPageFinished won't be called.
                true
            } else if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("smsto:") || url
                    .startsWith("mms:") || url.startsWith("mmsto:")
            ) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view?.context?.startActivity(intent)
                true // If we return true, onPageStarted, onPageFinished won't be called.
            } else if (url.startsWith("mailto:")) {
                val mt = MailTo.parse(url)
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/html"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mt.to))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, mt.subject)
                emailIntent.putExtra(Intent.EXTRA_CC, mt.cc)
                emailIntent.putExtra(Intent.EXTRA_TEXT, mt.body)
                context.startActivity(emailIntent)
                true
            } else if( url.contains("mastodon.social") || url == "https://neave.com/"){
                true
            }else {
                if (url.contains("facebook.com") ||
                    url.contains("instagram.com") ||
                    url.contains("youtube.com/channel") ||
                    url.contains("twitter.com") ||
                    url.contains("reddit.com") ||
                    url.contains("linkedin.com") ||
                    url.contains("pinterest.com") ||
                    // url.contains("mastodon.social") ||
                    url.contains("whatsapp.com")
                ) {
                    try {
                        view?.context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        true
                    } catch (e: Exception) {
//                            false
                        true
                    }
                }/* else if (mOpenPurposeType == OPEN_PURPOSE.SHOW_BLOCKERX_SUPPORT && !url.contains("blockerx")) {
                        true
                    }*/ else {
                    super.shouldOverrideUrlLoading(view, url)
                }
            }
        } else {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    override fun onLoadResource(view: WebView?, url: String?) {
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
    }
}
