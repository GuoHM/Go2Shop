const MergeJsonWebpackPlugin = require('merge-jsons-webpack-plugin');
// custom webpack config
module.exports = (config) => {
  config.plugins.push(
    new MergeJsonWebpackPlugin({
      output: {
        groupBy: [
          { pattern: './src/assets/i18n/en/*.json', fileName: 'assets/i18n/en.json' }
        ]
      }
    })
  );
  return config;
};
