/**
 * {{text | textcut(10, '///')}}
 */
export default (value, len = 30, ellipsis = '...') => {
  if (!value) return '';
  if (value.length > len) {
    return `${value.slice(0, len)}${ellipsis}`;
  }
  return value;
};
