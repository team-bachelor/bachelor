import Mock from 'mockjs';
/**
 * 权限管理/example
 */
Mock.mock(/\/roles/, {
  code: 0,
  msg: '',
  status: 'OK',
  'data|7': [
    {
      'id|+1': 1,
      'code|+1': 1,
      'name|+1': '@cname(2, 3)',
      'orgCode|+1': '@cname(2, 3)',
    },
  ],
});
Mock.mock(/users/, {
  code: 0,
  msg: '',
  status: 'OK',
  'data|1-10': [
    {
      'id|+1': 1,
      'code|+1': 1,
      'account|+1': '@cname(2, 3)',
      'username|+1': '@cname(2, 3)',
      'orgName|+1': '@cname(2, 3)',
    },
  ],
});
Mock.mock(/role\//, 'get', {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
    'id|+1': 1,
    'code|+1': 1,
    'name|+1': '@cname(2, 3)',
    'org|+1': '@cname(2, 3)',
  },
});
Mock.mock(/role/, 'post', {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
  },
});
Mock.mock(/role_user/, 'post', {
  code: 0,
  msg: '',
  status: 'OK',
});
Mock.mock(/role_user/, 'put', {
  code: 0,
  msg: '',
  status: 'OK',
});
Mock.mock(/role_user/, 'get', {
  code: 0,
  msg: '',
  status: 'OK',
  'data|1-10': [
    {
      'id|+1': 1,
      'account|+1': '@cname(2, 3)',
      'username|+1': '@cname(2, 3)',
      'orgName|+1': '@cname(2, 3)',
    },
  ],
});

Mock.mock(/orgs/, {
  code: 0,
  msg: '',
  status: 'OK',
  'data|1-10': [
    {
      'id|+1': 1,
      'name|+1': '@cname(4, 5)',
    },
  ],
});
Mock.mock(/permissions/, {
  code: 0,
  msg: '',
  status: 'OK',
  'data|4-10': [
    {
      'id|+1': 1,
      'groupCode|+1': 1,
      'groupName|+1': '@cname(4, 5)',
      'perms|2-10': [
        {
          'id|+1': 1,
          'objCode|+1': '@word(3,5)',
          'objOperate|+1': '@cname(4, 5)',
          'objUri|+1': '@cname(4, 5)',
          'owner|+1': '@cname(4, 5)',
          has: '@boolean',
        },
      ],
    },
  ],
});

Mock.mock(/role_permission/, {
  code: 0,
  msg: '',
  status: 'OK',
  'data|4-10': [
    '@word(3,5)',
  ],
});
