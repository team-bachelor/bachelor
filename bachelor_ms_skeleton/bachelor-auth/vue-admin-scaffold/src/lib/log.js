/* eslint no-use-before-define: 0 */
/**
 * -------------------------------
 * log输出及向服务端发送
 * -------------------------------
 */
import axios from 'axios';

let logs = [];
let timer = null;
const config = {
  url: '/api/log',
  delay: 30000,
  enable: false,
};

/**
 * 定时保存
 */
function timing() {
  clearTimeout(timer);
  timer = setTimeout(fetch, config.delay);
}
/**
 * 提交至服务
 */
function fetch() {
  if (!logs.length || !config.enable) {
    timing();
    return;
  }
  // 发送 POST 请求
  axios({
    method: 'post',
    url: config.url,
    data: JSON.stringify(logs),
  }).then(() => {
    logs = [];
    timing();
  }).catch(() => {
    timing();
  });
}

function log(...args) {
  if (args.length === 0) {
    return;
  }
  Function.prototype.apply.call(console.log, console, args);
  for (let i = 0, size = args.length; i < size; i += 1) {
    logs.push(`${new Date().getTime()}:${args[i]}`);
  }
}

export default function plugin(Vue, options) {
  if (plugin.installed) return;
  plugin.installed = true;
  Object.assign(config, options);

  Vue.Log = log;
  Vue.config.errorHandler = (err, vm, info) => {
    log(`${info} ${err}`);
  };
  Object.defineProperties(Vue.prototype, {
    $log: {
      get() {
        return log;
      },
    },
  });
  timing();
}
