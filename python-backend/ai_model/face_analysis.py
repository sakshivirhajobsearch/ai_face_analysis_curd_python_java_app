from deepface import DeepFace

def analyze_face(image_path):
    result = DeepFace.analyze(img_path=image_path, actions=['age', 'gender', 'emotion'])
    return {
        "age": result[0]["age"],
        "gender": result[0]["gender"],
        "emotion": result[0]["dominant_emotion"]
    }
