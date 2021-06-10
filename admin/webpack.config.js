// Generated using webpack-cli http://github.com/webpack-cli
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack')

module.exports = {
    mode: 'development',
    entry: './src/index.js',
    output: {
        path: path.resolve(__dirname, 'dist'),
    },
    devServer: {
        open: true,
        host: 'localhost',
    },
    // plugins: [
    //     new HtmlWebpackPlugin({
    //         template: 'index.html',
    //     }),
    //
    //     // Add your plugins here
    //     // Learn more obout plugins from https://webpack.js.org/configuration/plugins/
    // ],
    module: {
        rules: [
            {
                test: /\\.(js|jsx)$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
            },
            {
                test: /\.css$/i,
                // use: ['style-loader', 'css-loader', 'postcss-loader'],
                loader: 'style-loader',
                options: {
                    modules : false
                }

            },
            {
                test: /\.(eot|svg|ttf|woff|woff2|png|jpg|gif)$/,
                type: 'asset',
            },

            // Add your rules for custom modules here
            // Learn more about loaders from https://webpack.js.org/loaders/
        ],
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
        })
    ]
};
