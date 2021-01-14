const req = require.context('../../assets/icons', false, /\.png$/);
const requireAll = requireContext => requireContext.keys();

const re = /\.\/(.*)\.png/;

const icons = requireAll(req).map(i => i.match(re)[1]);
export default icons;
