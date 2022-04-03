$(document).ready(function() {

    function getStatuses() {
        let statuses = $('input:checkbox[name="status"]:checked').map(function() {
            return this.value;
        }).get();
        if (statuses.length === 0) {
            statuses = $('input:checkbox[name="status"]').map(function() {
                return this.value;
            }).get();
        }
        return statuses;
    }

    function getStartEndTime() {
        let startDateTime = new Date(Date.now());
        let timeDifference = $('input[name="event-time"]:checked').val();
        let endDateTime = new Date(Date.now());
        if (timeDifference === '1') {
            endDateTime = new Date(endDateTime.setHours(startDateTime.getHours() + 1));
        }
        else if (timeDifference === '24') {
            endDateTime = new Date(endDateTime.setHours(23,59,59));
        }
        else {
            startDateTime = new Date(2022,0);
            endDateTime = new Date(9999,11);
        }
        return {
            start: moment(startDateTime).format().slice(0, 16),
            end: moment(endDateTime).format().slice(0, 16)
        }
    }

    let token = $('input[name="_csrf"]').attr('value');

    let table = $('#eventTable').DataTable({
        serverSide: true,
        processing: true,
        ordering: false,
        lengthChange: false,
        search: {
            return: true
        },
        language: {
                search: "Filter by patient name: "
        },
        ajax: {
            url: '/event/all',
            type: 'POST',
            headers: {
                'X-CSRF-TOKEN': token
            },
            data: function(data) {
                return {
                    draw: data.draw,
                    start: data.start,
                    length: data.length,
                    search: data.search.value,
                    startDateTime: getStartEndTime().start,
                    endDateTime: getStartEndTime().end,
                    statuses: getStatuses()
                }
            }
        },
        columns: [
            {
                data: "dateTime",
                render: function(data) {
                    return moment(data).format('D MMMM, dddd')
                }
            },
            {
                data: "dateTime",
                render: function(data) {
                    return moment(data).format('HH:mm')
                }
            },
            { data: "patient.name" },
            { data: "treatment.name" },
            {
                data: "eventStatus",
                render: function(data) {
                    return data.charAt(0).toUpperCase() + data.toLowerCase().slice(1);
                }
            },
            { data: "cancelReason" },
            {
                data: "uuid",
                render: function ( data, type, row, meta ) {
                    return '<a href="/event/'+data+'" role="button" class="btn btn-sm btn-primary">Edit</a>';
                }
            }
        ]
    });

    $('input:radio').on('change', function () {
        table.draw();
    });

    $('input:checkbox').on('change', function () {
        table.draw();
    });
})