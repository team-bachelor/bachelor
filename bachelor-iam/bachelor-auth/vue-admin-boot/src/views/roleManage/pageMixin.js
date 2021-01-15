const paginationParams = () => ({
  pageSize: 10,
  currentPage: 1,
  total: 0,
  isFinish: false,
});
export default {
  data() {
    return {
      pagination: paginationParams(),
    };
  },
  methods: {
    resetPagination() {
      this.pagination = paginationParams();
    },
    onCurrentPageChange(e, func) {
      this.pagination.currentPage = e;
      if (func) func();
    },
  },
};
