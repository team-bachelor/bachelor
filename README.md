## bachelor快速开发框架

### 目录介绍

#### 1. bachelor-dependencies
&emsp;管理框架中用到的所有引用。

#### 2. bachelor-fw
&emsp;核心框架。

##### &emsp;1) bachelor-fw-core
&emsp;&emsp;核心框架核心组件，包括错误处理机制，上下文处理机制，核心事件处理等。

##### &emsp;2) bachelor-fw-web
&emsp;&emsp;核心框架WEB支持，包括国际化支持，web上下文处理机制，错误处理，json处理，翻页，面向用户的消息处理。

#### &emsp;3. bachelor-fw-ext
&emsp;核心框架扩展，即技术类扩展。

##### &emsp;1) bachelor-fw-cache-redis
&emsp;&emsp;redis扩展。

#### 4. bachelor-oauth2
&emsp;实现了OAuth2协议的登录支持和接口调用的规范与默认实现。

#### 5. bachelor-acm
&emsp;系统访问管理和访问控制模块（权限管理），包括了基于角色的权限管理模块和JWT相关功能，访问控制功能，及前端页面样例和与默认用户平台交互的服务层组件。

#### 6. bachelor-ms
&emsp;微服务架构体系实例,其中注册中心和网关经过改造。注册中心增加了可以显示swagger-ui的功能；网关增加了JWT解析功能。

#### 7. bachelor-demo
&emsp;基于该框架的各种实例（待更新）。

