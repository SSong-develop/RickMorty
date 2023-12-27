#!/usr/bin/env python
import subprocess


def auto_commit():
    # 사용자로부터 커밋 메시지를 입력 받음
    commit_message = input("커밋 메시지를 입력하세요: ")

    try:
        # git add 실행
        subprocess.run(["git", "add", "."], check=True)

        # git commit 실행
        subprocess.run(["git", "commit", "-m", commit_message], check=True)

        print("커밋이 완료되었습니다.")
    except subprocess.CalledProcessError as e:
        print(f"오류가 발생하여 커밋에 실패했습니다. 오류 메시지: {e}")


if __name__ == "__main__":
    auto_commit()
