/**
 * 创建restful服务
 *
 * >> 配置
 * createRestful('user')
 * createRestful('user', {key: 'userid'})
 *
 * >> 调用
 * GET     /creature           => Creature.all({pageNo: 2})
 * GET     /creature/1         => Creature.show(1)
 * GET     /creature/1         => Creature.show({userId: 1})
 * POST    /creature           => Creature.create({})
 * PUT     /creature/1         => Creature.update(1)
 * DELETE  /creature/1         => Creature.destroy(1)
 * DELETE  /creature         => Creature.destroys({ids: [1,2,3]})
 */
const defaultOptions = {
  key: 'id',
};
export default (path, options) => {
  const opts = Object.assign(defaultOptions, options);
  return {
    all: { url: `/${path}`, method: 'get' },
    show: { url: `/${path}/:${opts.key}`, method: 'get' },
    create: { url: `/${path}`, method: 'post' },
    update: { url: `/${path}/:${opts.key}`, method: 'put' },
    destroy: { url: `/${path}/:${opts.key}`, method: 'delete' },
    destroys: { url: `/${path}`, method: 'delete' },
  };
};
