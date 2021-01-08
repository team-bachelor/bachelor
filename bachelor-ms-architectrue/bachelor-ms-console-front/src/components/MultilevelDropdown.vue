<template>
  <el-dropdown class="multileve-dropdown" v-bind="$props" @command="command">
    <slot/>
    <el-dropdown-menu slot="dropdown">
      <multileve-dropdown-item v-for="(item,index) in data"
        :item=item :key=index :props="props" />
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
const MultileveDropdownItem = {
  name: 'MultileveDropdownItem',
  props: {
    item: Object,
    props: Object,
  },
  render() {
    const children = this.item[this.props.children];
    const eachSubItem = items => items.map((item, i) =>
      <multileve-dropdown-item key={i} item={item} props={this.props}/>);
    const subItems = children && children.length ? eachSubItem(children) : [];
    return (
      <div class="multileve-dropdown-item">
        <el-dropdown-item command={this.item[this.props.key]}>
          <span>{this.item[this.props.label]}</span>
        </el-dropdown-item>
        <div class="children">{subItems}</div>
      </div>
    );
  },
};
export default {
  name: 'MultilevelDropdown',
  components: { MultileveDropdownItem },
  props: {
    data: Array,
    trigger: String,
    size: String,
    props: {
      type: Object,
      default() {
        return {
          label: 'label',
          key: 'key',
          children: 'children',
        };
      },
    },
  },
  methods: {
    command(e) {
      // if (!e) return;
      let matched = null;
      const eachItem = (items) => {
        items.some((item) => {
          if (item[this.props.key] === e) {
            matched = item;
            return true;
          } else if (item[this.props.children]) {
            eachItem(item[this.props.children]);
          }
          return false;
        });
      };
      eachItem(this.data);
      if (matched) this.$emit('command', matched);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/{
  .multileve-dropdown-item{
    .el-dropdown-menu__item:hover{
      background: transparent;
    }
    .children{
      padding-left: 20px;
    }
  }
}
</style>
