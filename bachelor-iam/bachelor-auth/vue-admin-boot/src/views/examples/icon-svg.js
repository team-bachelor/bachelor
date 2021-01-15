const req = require.context('../../assets/svg/source', false, /\.svg$/);
const requireAll = requireContext => requireContext.keys();

const re = /\.\/(.*)\.svg/;

const svgIcons = requireAll(req).map(i => i.match(re)[1]);
export default svgIcons;
