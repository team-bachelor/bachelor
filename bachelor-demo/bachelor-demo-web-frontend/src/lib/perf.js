/**
 * -------------------------------
 * 用户体验收集
 * -------------------------------
 */
export default function plugin(Vue) {
  if (plugin.installed) return;
  plugin.installed = true;

  const data = {};
  function save(e) {
    Vue.Log(JSON.stringify(e));
  }
  function start(name, desc) {
    if (!name) return;
    data[name] = {
      start: new Date().getTime(),
      end: 0,
      duration: 0,
      desc: desc || '',
    };
  }
  function end(name) {
    if (!name || !data[name]) return;
    data[name].end = new Date().getTime();
    data[name].duration = data[name].end - data[name].start;
    save(data[name]);
  }
  Vue.Perf = {
    start,
    end,
  };
  Object.defineProperties(Vue.prototype, {
    $perf: {
      get() {
        return Vue.Perf;
      },
    },
  });
}
