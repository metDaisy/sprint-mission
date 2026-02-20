# 10-spring-mission-4

mission-4 start!

# 구현

- 기능별로 package 를 구성했다.
- `controller`
  - `UserController`
  - `AuthController`
  - `ChannelController`
  - `MessageController`
- `service`
    - `AuthService`
    - `BinaryContentService`
    - `DomainService`
    - `UserService`
    - `ChannelService`
    - `MessageService`
    - `ReadStatusService`
    - `UserStatusService`
- `repository`
    - `DomainRepository`
    - `UserRepository`
    - `ChannelRepository`
    - `MessageRepository`
    - `UserStatusRepository`
    - `ReadStatusRepository`
    - `BinaryContentRepository`
- `entity`
    - `User`
    - `UserStatus`
    - `Channel`
    - `ChannelType`
    - `ReadStatus`
    - `ReadType`
    - `Message`
    - `BinaryContent`
- `dto`
    - `UserServiceDTO`
    - `AuthServiceDTO`
    - `BinaryContentServiceDTO`
    - `ChannelServiceDTO`
    - `MessageServiceDTO`
    - `ReadStatusServiceDTO`
    - `UserStatusServiceDTO`
- `test`
  - `controller`
    - `BaseControllerTest`
    - `UserControllerTest`
  - `fixture`
    - `UserFixture`
    - `BinaryContentFixture`
- Todo
    - toJson
    - encryption, decryption