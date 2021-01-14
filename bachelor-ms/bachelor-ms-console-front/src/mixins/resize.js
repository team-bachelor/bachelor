export default {
  mounted() {
    window.addEventListener('resize', () => {
      const { onResize } = this.$options;
      if (onResize) {
        const mainEl = window.document.getElementsByClassName('lx-main');
        const { clientWidth, clientHeight } = mainEl.item(0);
        onResize(clientWidth, clientHeight);
      }
    });
  },
};
