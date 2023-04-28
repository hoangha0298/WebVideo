## Hướng dẫn cài đặt lần đầu chạy project:

1. Copy 2 file codeStyleConfig.xml và Project.xml vào thư mục .idea\codeStyles.
2. Tải ffmpeg https://ffmpeg.org/download.html và cài đặt (có phiên bản riêng cho linux và windows).

## Các Tài nguyên của dự án

- Source code:
    - Repository: https://github
    - Ngôn ngữ: Java(8)
    - Framework: Spring boot, ffmpeg(cài trên hệ điều hành)

## Luồng GIT và CI/CD
- Git branch:
    - branch chính develop: branch develop
    - branch chính production: branch main

- Quy trình phát triển tính năng:
    1. Checkout từ môi trường production.
    2. Phát triển tính năng.
    3. Merge vào develop.

- CI/CD: làm sau