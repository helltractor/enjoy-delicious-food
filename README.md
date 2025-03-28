<p align="center">
	<strong>精彩享食</strong>
</p>
<p align="center">
    <a target="blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src="https://img.shields.io/github/stars/helltractor/enjoy-delicious-food.svg?style=social" alt="github star"/>
    </a>
    <a target="_blank" href="https://opensource.org/licenses/MIT">
        <img src="https://img.shields.io/:license-MIT-blue.svg" alt="license"/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src='https://img.shields.io/badge/JDK-1.8.0_40+-green.svg' alt='jdk'/>
    </a>
</p>
<p align="center">
    <a target="blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src='https://img.shields.io/badge/Maven-3.9.6-blue.svg' alt='maven'/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/enjoy-delicious-food">
        <img src='https://img.shields.io/badge/Spring%20Boot-2.7.3-green.svg' alt='spring boot'/>
    </a>
</p>

# Build project

1. `mvn clean install`
2. `cd ./build`
3. `docker-compose up -d`

# Run project

1. run `EnjoyApplication.java`
2. open `http://localhost/#/login` in browser

---

1. `java -jar server-${version}.jar`
2. open `http://localhost/#/login` in browser

# Next steps

- [ ] 数据库无数据时，能够导出数据表格
- [ ] 数据库主从复制，参考[使用docker-compose配置mysql主从备份](https://www.enjoytoday.cn/2024/01/11/%E4%BD%BF%E7%94%A8docker-compose%E9%85%8D%E7%BD%AEmysql%E4%B8%BB%E4%BB%8E%E5%A4%87%E4%BB%BD/)
