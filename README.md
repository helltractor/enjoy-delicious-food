<p align="center">
	<strong>精彩享食</strong>
</p>

<p align="center">
    <a target="blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src="https://img.shields.io/github/stars/helltractor/enjoy-delicious-food.svg?style=social" alt="GitHub Star"/>
    </a>
    <a target="_blank" href="https://opensource.org/licenses/MIT">
        <img src="https://img.shields.io/:license-MIT-blue.svg" alt="License"/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src="https://img.shields.io/badge/JDK-1.8.0_40+-green.svg" alt="JDK Version"/>
    </a>
</p>

<p align="center">
    <a target="blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src="https://img.shields.io/badge/Maven-3.9.6-blue.svg" alt="Maven"/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src="https://img.shields.io/badge/Spring%20Boot-2.7.3-green.svg" alt="Spring Boot"/>
    </a>
</p>

---

## 📦 构建项目

```sh
mvn clean install
cd ./build
docker-compose up -d
```

## 🚀 运行项目

```sh
java -jar server-${version}.jar
```

然后在浏览器中访问：

```
http://localhost/#/login
```

---

## 🔥 下一步计划

- [ ] 数据库为空时支持导出数据表
- [x] 数据库主从复制支持
  - [基于 Docker-Compose 搭建 MySQL 主从复制](https://www.cnblogs.com/haima/p/14341903.html)
  - [MySQL 主从复制详解（原理 + 实践）](https://www.cnblogs.com/wzh2010/p/15049805.html)
