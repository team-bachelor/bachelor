<template>
    <transition-group
        :css="false"
        :tag="tag"
        @before-enter="beforeEnter"
        @enter="enter"
        @leave="leave">
        <slot/>
    </transition-group>
</template>

<script>
import Velocity from 'velocity-animate';

export default {
  name: 'transition-stagger',
  props: {
    tag: {
      type: String,
      required: false,
      default: 'ul',
    },
    delay: {
      type: Number,
      required: true,
    },
    beforeEnterStyles: {
      type: Object,
      required: true,
    },
    enterStyles: {
      type: Object,
      required: true,
    },
    leaveStyles: {
      type: Object,
      required: true,
    },
    options: {
      type: Object,
      required: false,
      default() {
        return {};
      },
    },
    enterAtOnce: {
      type: Boolean,
      default: false,
    },
    leaveAtOnce: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      count: 0,
    };
  },
  methods: {
    beforeEnter(el) {
      Velocity(el, this.beforeEnterStyles);
    },
    enter(el, done) {
      if (this.enterAtOnce) {
        this.count = 0;
      }
      this.animate(el, this.enterStyles, done);
    },
    leave(el, done) {
      if (this.leaveAtOnce) {
        this.count = 0;
      }
      this.animate(el, this.leaveStyles, done);
    },
    animate(el, styles, done) {
      Velocity(
        el,
        styles,
        Object.assign(this.options, {
          complete: done,
          delay: this.count * this.delay,
        }),
      );
      this.count += 1;
    },
  },
  watch: {
    count(val) {
      setTimeout(() => {
        this.count = 0;
      }, val * this.delay);
    },
  },
};
</script>
