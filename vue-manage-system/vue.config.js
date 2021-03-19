module.exports= {
    devServer: {
        proxy: {
            "/api": {
                target: 'https://applet.konicaminolta-bcn.cn',
                ws:true,
                changeOrigin: true,
                pathRewrite: {
                    "^/api": "/"
                }
            }
        }
    }
}
