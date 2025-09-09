from deepface import DeepFace

def analyze_expression(image_path):
    result = DeepFace.analyze(img_path=image_path, actions=['emotion'])
    return {
        "dominant_emotion": result[0]["dominant_emotion"],
        "emotion_scores": result[0]["emotion"]
    }
