// vue.config.js
module.exports = {
  publicPath: '/',
  devServer: {
    // proxy: 'http://192.168.37.74:8889',
    proxy: 'http://192.168.1.104\n:8888/',
  },
};
