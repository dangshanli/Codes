from pytube import YouTube

# where to save
SAVE_PATH = "E:\\Downloads"  # to_do

# link of the video to be downloaded
link = "https://www.youtube.com/watch?v=GtmW0BfKpA8"
link = 'https://www.youtube.com/watch?v=j7sHW_z8aVo'
link = 'https://www.youtube.com/watch?v=iV0EHtrYlY8'
link = 'https://www.youtube.com/watch?v=QQ2nKT9LkwQ'

try:

    yt = YouTube(link)
    yt.streams.filter(progressive=True, file_extension='mp4') \
        .order_by('resolution') \
        .desc() \
        .first() \
        .download(output_path=SAVE_PATH)
except:
    print("Connection Error")  # to handle exception

print('Task Completed!')
