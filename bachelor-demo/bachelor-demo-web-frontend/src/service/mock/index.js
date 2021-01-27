import Mock from 'mockjs';
import './role';

Mock.setup({
  timeout: '200-1000',
});
Mock.mock('/login', 'post', {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
    token: '@string',
  },
});
Mock.mock(/user\/logout/, 'put', {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
    token: '@string',
  },
});
Mock.mock(/user\/accesstoken/, {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
    token: 'eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcGVuSWQiOiJZakUyTkdVNU5qRm1OR016TkRVd1ltSmlaakkzTVRkalltSTNNMkUzWWpGQU16QTVaakU0TkdJeE4ySmlORFpoT1dGalpUZzFOalZsWldOaE1EQXpORGM9IiwidXNlcklkIjoiYjE2NGU5NjFmNGMzNDUwYmJiZjI3MTdjYmI3M2E3YjEiLCJ1c2VybmFtZSI6Imxpbm1pbmd4aW9uZyIsImFjY291bnQiOiJsaW5taW5neGlvbmciLCJhY2Nlc3N0b2tlbiI6IlY5OTdsaDkyT2hjSXRla3o3eEF5d0NIWEVkOEFSblV0SHJSZndNNGpabTJrakVhMzl6R0Q1US9SYnlsQ3NUcG4yQjk3aE8yQjJCd0oxQUlkWWhSVUdoYTRMZWptaEd0bGd3SEV4Z2hRc1djNFp2WlhlaWUxVXFGUUw3U1NPcjlub1paZG1zSjJlaTNqRjBtaGhOR1RuNTU2cHIwMkJYNk5CWHA2M2RXMUVydk45WElBOGozaVMyQjJaS01aaG54MkJHVDdvWkRBa1dxTWhmNzFTTzAyQmhXN2Zkdm03OW4zUXJIeHBNdWJKRWVvbldNRFAwQ3NBQ3UvYWpTRlBKMGxJZDQ5eHJETXZxMXBRbHV6a2lmMEViRWY5TnAzL2VFRjVzSVNPbVFTWlQwSlVYbWhHRkI3dDlVVTZUNS9HRjN4Vjc3WkZITktnbTl6MVdFTGJKVUtCeE9aMVZlN0E9PSIsImV4cCI6MTU0NDg2Njk2ODY2MywiaWF0IjoxNTQ0NzgwNTY4NjYzLCJpc3MiOiIiLCJzdWIiOiJsaW5taW5neGlvbmciLCJhdWQiOiIiLCJuYmYiOiIiLCJqdGkiOiIiLCJ1c2VyX25hbWUiOiJsaW5taW5neGlvbmciLCJ1c2VyX2NvZGUiOiJsaW5taW5neGlvbmciLCJvcmdfaWQiOiIxNjA3MmZkYjhkZTU0NmJkODcyOTJmMzQ2YWRlYTA3ZiIsIm9yZ19jb2RlIjoibHZ4aW5nc2hlIiwib3JnX25hbWUiOiLml4XooYznpL4ifQ.Ju7fkpBIxaMRChkYfijlx2LXKQMfM-xAMvooETufoYn3_LopM8gEVmRNcHsNbIJgzOb2l5R-Rrw_PaoKgpp3uFAmMQ3hJVeR-kYYQFsA_gZ84Np3c4ReTzlQgff0kYi4YFHdlKPmjfEYHIGSRAr5wuYCWTuqnR6A5eL8S1Oabzbn7mP_6vUUvVwNHk5sf1BdR_IZr7KF4RlveGf87jwC8WjnJ3Mn9fSs7BVXmjCFzYxa-7RtIUNKhciVy-7mPfy9mrJTYsuEUoA9XKYG0hFlyUI9uADeLO4Ib1u5R-jnGQCnAT1fbO3ISTrqQ9FNcac2Wflyf1pmwAb4k_eObU3tIw',
  },
});
Mock.mock(/^\/user\/show/, {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
    username: '@cname(2, 5)',
    headimg: '@image("100x100", "#50B347", "#FFF", "Mock.js")',
  },
});
Mock.mock(/user_permission/, {
  code: 0,
  status: 'OK',
  msg: '',
  data: [{
    objCode: 'deleteUser',
  }, {
    objCode: 'addUser',
  }, {
    objCode: 'menu.home',
  }, {
    objCode: 'menu.role',
  }],
});
Mock.mock(/roles\/[A-Za-z0-9]+/, {
  code: 0,
  msg: '',
  status: 'OK',
  data: {
    role_code: 'role_code',
  },
});
Mock.mock(/user_menu/, {
  code: 0,
  msg: '',
  status: 'OK',
  data: [{
    title: '角色管理',
    path: '/',
    code: 'menu.home',
  }, {
    title: '一级菜单2',
    path: '/a',
    code: 'menu.home',
    children: [{
      title: '二级菜单1',
      path: '/a/a',
      code: 'menu.home',
    }],
  }, {
    title: '一级菜单3',
    path: '/b',
    code: 'menu.home',
    children: [{
      title: '二级菜单1',
      path: '/b/a',
      code: 'menu.home',
      children: [{
        title: '三级菜单',
        path: '/b/a/a',
        code: 'menu.home',
      }],
    }, {
      title: '二级菜单2',
      path: '/b/b',
      code: 'menu.home',
      children: [{
        title: '三级菜单',
        path: '/b/b/a',
        code: 'menu.home',
      }],
    }, {
      title: '二级菜单3',
      path: '/b/c',
      code: 'menu.home',
    }],
  }],
});

Mock.mock(/message/, {
  code: 0,
  msg: '',
  status: 'OK',
  'data|1-10': [
    {
      'id|+1': '@id',
      'title|+1': '@ctitle',
      'content|+1': '@csentence(50, 300)',
      'datetime|+1': '@datetime',
    },
  ],
});
