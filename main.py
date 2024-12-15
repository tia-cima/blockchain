import hashlib
import time

import requests
from datetime import datetime, timezone

API_URL = "http://localhost:8080"
GET_MEMPOOL_URL = f"{API_URL}/transactions/get"
GET_LATEST_BLOCK_URL = f"{API_URL}/blockchain/get/latest"
SUBMIT_BLOCK_URL = f"{API_URL}/blockchain/add"
FETCH_INTERVAL = 1
credits = 0

if __name__ == "__main__":
    while True:
        responseTransactions = requests.get(GET_MEMPOOL_URL)
        if responseTransactions.status_code == 200:
            transactions = responseTransactions.json()
        else:
            transactions = []
        if transactions:
            print("Mining a new block...")
            response = requests.get(GET_LATEST_BLOCK_URL)
            if response.status_code == 200:
                latest_block = response.json()
                difficulty = latest_block["difficulty"]
                index = latest_block["index"] + 1
                previous_hash = latest_block["hash"]
                nonce = 0
                timestamp = datetime.now(timezone.utc).isoformat()
                prefix = "0" * int(difficulty)

                while True:
                    formatted = ""
                    for tx in transactions:
                        formatted += f"{tx['sender']}{tx['recipient']}{tx['amount']}{tx['data']}{tx['timestamp']}"
                    block_data = f"{index}{timestamp}{formatted}{previous_hash}{nonce}{difficulty}"
                    new_hash = hashlib.sha256(block_data.encode('utf-8')).hexdigest()
                    if new_hash.startswith(prefix):
                        mined_block = {
                            "index": index,
                            "timestamp": timestamp,
                            "transactions": transactions,
                            "previousHash": previous_hash,
                            "hash": new_hash,
                            "nonce": nonce,
                            "difficulty": 0
                        }
                        response = requests.post(SUBMIT_BLOCK_URL, json=mined_block)
                        if response.status_code == 200:
                            reward = int(response.text.strip())
                            print(f"Block mined! You have been rewarded with {reward} credits! New amount of credits: {credits + reward}")
                            credits += reward
                            break
                        else:
                            print(f"Error submitting the block: {response.status_code}")
                            break
                    nonce += 1
            else:
                print(f"Error fetching the latest block: {response.status_code}")
        time.sleep(FETCH_INTERVAL)


